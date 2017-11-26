/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaexpertomedico;

import java.util.ArrayList;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Util;

/**
 *
 * @author IsaacGS
 */
public class SistemaExperto {

    private String baseConocimiento;
    private String sistemaExperto;
    private static final String QUESTION_FORMAT = "¿Es verdad que %s ?";
    private SistemaExperto.CallBackMain callBackMain;
    private Query conocimiento;
    private Map<String, Term>[] data;
    private Term sintomaPreguntado;
    private Term enfermedadPreguntada;
    private boolean isDiagnostic;
    private ArrayList<String> sintomasEncontrados = new ArrayList<>();

    public SistemaExperto(CallBackMain callBackMain) {
        this.callBackMain = callBackMain;
        String experto = "consult('expertojava.pl')";//aqui colocan el nombre de su archivo a compilar
        String conocimiento = "consult('conocimiento.pl')";//aqui colocan el nombre de su archivo a compilar

        Query q1 = new Query(experto);
        Query q2 = new Query(conocimiento);
        System.out.println(experto + " " + (q1.hasSolution() ? "true" : "false")); //mostrara mensaje  si hay o no conexion           
        System.out.println(conocimiento + " " + (q2.hasSolution() ? "true" : "false")); //mostrara mensaje  si hay o no conexion     

        if (q1.hasSolution() && q2.hasSolution()) {
            System.out.println("------------->Sistema Cargado correctamente");
            loadDataBase();
        } else {
            System.out.println("------------->El sistema experto no se pudo cargar");
        }
    }

    public boolean loadDataBase() {

        conocimiento = new Query("conocimiento(X,Y)");
        System.out.println("---Goal-->" + conocimiento.goal());
        if (conocimiento.hasSolution()) {
            data = conocimiento.allSolutions();
            System.out.println("lenght solution: " + conocimiento.allSolutions().length);

        }

        return conocimiento.hasSolution();
    }

    public void startQuestions() {

        boolean questionSuccess = false;
        boolean responseFalse = false;
        callBackMain.setQuestion("");
        for (int i = 0; i < data.length; i++) {
            System.out.println("----->" + Util.toString(data[i]));
            System.out.println("i: " + i);
            Map<String, Term> solution = data[i];
            System.out.println("X = " + solution.get("X"));
            Term sintomas = solution.get("Y");
            Term enfermedad = solution.get("X");

            System.out.println("length: " + sintomas.toTermArray().length);
            Term[] sintomasArray = sintomas.toTermArray();

            for (int j = 0; j < sintomasArray.length; j++) {

                System.out.println("--Argument--->" + sintomasArray[j]);
                Query q5 = new Query("prueba_verdad_de(" + enfermedad
                        + ", " + sintomasArray[j] + ")");

                System.out.println("-----Goal--->" + q5.goal());
                System.out.println("-----Sol--->" + q5.hasSolution());

                if (q5.hasSolution()) {
                    Query q6 = new Query("is_response_false(" + enfermedad
                            + ", " + sintomasArray[j] + ")");
                    System.out.println("----------response false------->" + q6.hasSolution());

                    if (q6.hasSolution()) {
                        break;
                    }

                } else {
                    callBackMain.setQuestion(String.format(QUESTION_FORMAT, sintomasArray[j]));
                    sintomaPreguntado = sintomasArray[j];
                    enfermedadPreguntada = enfermedad;
                    questionSuccess = true;
                    break;
                }

            }

            if (questionSuccess) {
                break;
            }
        }

        if (!questionSuccess) {
            callBackMain.finish();
        }
    }

    public void setAnswer(final String answer) {
        String consulta = "";

        switch (answer) {
            case "si":
                consulta = "asserta(conocido(" + sintomaPreguntado + "))";
                break;

            case "no":
                consulta = "asserta(conocido(is_false(" + sintomaPreguntado + ")))";
                break;

            case "porque":
                callBackMain.showDialog("Estoy evaluando la hipotesis de la enfermedad: "
                        + enfermedadPreguntada
                        + "\n\nPara esto necesito saber si " + sintomaPreguntado);

                break;
        }

        if (!consulta.isEmpty()) {
            Query query = new Query(consulta);
            System.out.println("--->Goal:" + query.goal());
            System.out.println("--->Sol: " + query.hasSolution());
        }

    }

    public boolean findDiagnostic() {
        System.out.println("***********************************");
        System.out.println("**************INICIO*********************");
        System.out.println("***********************************");

        ArrayList<Boolean> validador = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            boolean aux = true;
            validador.clear();
            sintomasEncontrados.clear();
            Map<String, Term> solution = data[i];
            Term sintomas = solution.get("Y");
            Term enfermedad = solution.get("X");

            Term[] sintomasArray = sintomas.toTermArray();

            for (int j = 0; j < sintomasArray.length; j++) {
                Query q5 = new Query("is_response_true(" + enfermedad
                        + ", " + sintomasArray[j] + ")");
                System.out.println("---GOAL------>" + q5.goal());
                System.out.println("<------------" + q5.hasSolution());
                validador.add(q5.hasSolution());
                if (q5.hasSolution()) {
                    sintomasEncontrados.add(sintomasArray[j].toString());
                }
            }

            for (Boolean b : validador) {
                aux &= b;
            }

            if (aux) {
                isDiagnostic = true;
                System.out.println("**************************************");
                System.out.println("**************************************");
                System.out.println("**************************************");
                System.out.println("--------------->Enfermedad encontrada: " + enfermedad);
                callBackMain.diagnosisCompleted(enfermedad.toString());

                return true;
            }
        }

        System.out.println("***********************************");
        System.out.println("***********FIN************************");
        System.out.println("***********************************");
        return false;
    }

    ArrayList<String> getSintomasEncontrados() {
        return sintomasEncontrados;
    }

    public interface CallBackMain {

        void setQuestion(String question);

        void diagnosisCompleted(String diagnosis);

        void showDialog(String message);

        void finish();
    }

}
