:- dynamic conocido/1.

prueba_verdad_de(Diagnosis, Sintoma):- conocido(Sintoma); conocido(is_false(Sintoma)).

is_response_false(Diagnosis, Sintoma):- conocido(is_false(Sintoma)).

is_response_true(Diagnosis, Sintoma):- conocido(Sintoma).

conocido(_):- fail.
not(X):- X,!,fail.
not(_).