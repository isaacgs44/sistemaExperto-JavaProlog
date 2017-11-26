package sistemaexpertomedico;

import java.util.ArrayList;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

public class SistemaExperto {

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
        String experto = "consult('expertojava.pl')";
        String conocimiento = "consult('conocimiento.pl')";

        Query q1 = new Query(experto);
        Query q2 = new Query(conocimiento);

        if (q1.hasSolution() && q2.hasSolution()) {
            System.out.println("El sistema se cargo correctamente!");
            loadDataBase();
        } else {
            System.out.println("El sistema no se pudo cargar correctamente!");
        }
    }

    public boolean loadDataBase() {
        conocimiento = new Query("conocimiento(X,Y)");
        if (conocimiento.hasSolution()) {
            data = conocimiento.allSolutions();
        }
        return conocimiento.hasSolution();
    }

    public void startQuestions() {
        boolean questionSuccess = false;
        callBackMain.setQuestion("");

        for (int i = 0; i < data.length; i++) {
            Map<String, Term> solution = data[i];
            Term sintomas = solution.get("Y");
            Term enfermedad = solution.get("X");
            Term[] sintomasArray = sintomas.toTermArray();

            for (int j = 0; j < sintomasArray.length; j++) {
                Query q1 = new Query("prueba_verdad_de(" + enfermedad + ", " + sintomasArray[j] + ")");

                if (q1.hasSolution()) {
                    Query q2 = new Query("is_response_false(" + enfermedad + ", " + sintomasArray[j] + ")");

                    if (q2.hasSolution()) {
                        break;
                    }

                } else {
                    callBackMain.setQuestion(String.format(QUESTION_FORMAT, sintomasArray[j]));
                    sintomaPreguntado = sintomasArray[j];
                    enfermedadPreguntada = enfermedad;
                    questionSuccess = true;
                    break;
                }

            } //Termina ciclo interno

            if (questionSuccess) {
                break;
            }
        }// Termina ciclo

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
            query.goal();
            query.hasSolution();
        }

    }

    public boolean findDiagnostic() {
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
                Query q1 = new Query("is_response_true(" + enfermedad + ", " + sintomasArray[j] + ")");
                validador.add(q1.hasSolution());
                if (q1.hasSolution()) {
                    sintomasEncontrados.add(sintomasArray[j].toString());
                }
            }

            for (Boolean b : validador) {
                aux &= b;
            }

            if (aux) {
                isDiagnostic = true;
                callBackMain.diagnosisCompleted(enfermedad.toString());
                return true;
            }
        }

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
