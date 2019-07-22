package sample;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;


/*
    todo :
      A seguinte sequência iterativa é definida pelo conjunto de inteiros positivos onde:
          n -> n/2 (se n é par)
          n -> 3n + 1 (se n é impar)
      Por exemplo, usando as regras acima e começando pelo número 13, nós geraríamos a seguinte sequência:
          13 40 20 10 5 16 8 4 2 1
      O que pode ser observado dessa sequência (começando no 13 e terminando no 1) é que ela contém 10 items.
      Embora ainda não esteja matematicamente provado, é esperando que, dado um numero inteiro positivo qualquer, a sequencia sempre chegará em 1.
      Pergunta: Qual inteiro positivo abaixo de 1 milhão produz a sequencia com mais items?
      Desafio: Crie um código na linguagem de sua preferência que calcule a resposta. Não é necessário codificar no editor online.
      Você pode usar o ambiente de sua escolha e colar a resposta no editor.
      Vamos avaliar a correção da solução, performance e legibilidade do código.
 */


public class Controller {

    class Response {
        int value = Integer.MIN_VALUE;
        List<Integer> sequence = new ArrayList<>();
    }


    @FXML
    private Label lblResponse ;


    private void showResult (Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("response: ")
                .append(response.value)
                .append("\n\nnumber of elements: ")
                .append(response.sequence.size())
                .append("\n\nsequence:\n[");

        int countElementsOnLine = 0;

        for(Integer value : response.sequence) {
            stringBuilder.append(value).append(" ");
            if (countElementsOnLine > 10) {
                stringBuilder.append("\n");
                countElementsOnLine = 0;
            }

            countElementsOnLine++;
        }

        stringBuilder.append("]");

        lblResponse.setText(stringBuilder.toString());
    }

    private List<Integer> generateSequence(int startValue) {
        List<Integer> sequence = new ArrayList<>();
        int currentValue = startValue;
        sequence.add(currentValue);

        while(currentValue > 1) {
            currentValue = (currentValue % 2 == 0) ? currentValue/2 : 3 * currentValue + 1;
            sequence.add(currentValue);
        }


        return sequence;
    }


    public void initialize() {

        implementation_1 ();
//        implementation_2 ();

    }


    private void implementation_1 () {
        Response response = new Response();

        for (int i=1; i<1000000; i++) {
            List<Integer> sequence = generateSequence(i);

            if (sequence.size() <= response.sequence.size()) {
                continue;
            }

            response.value = i;
            response.sequence = sequence;
        }

        showResult(response);
    }


    private void implementation_2 () {
        List<Response> responses = new ArrayList<>();
        final int unit = 100000;
        Response higherResponse = null;

        for (int i = 1; i <= 10; i++) {
            final int index = i;
            new Thread(() -> responses.add(calculateSequence((index-1)*unit, index*unit))).run();
        }

        for(Response response : responses) {
            if (higherResponse == null || response.sequence.size() > higherResponse.sequence.size()) {
                higherResponse = response;
            }
        }

        showResult(higherResponse);
    }

    Response calculateSequence (int startValue, int endValue) {
        Response responseOfGroup = new Response();

        for (int i=startValue; i<endValue; i++) {
            List<Integer> sequence = generateSequence(i);

            if (sequence.size() <= responseOfGroup.sequence.size()) {
                continue;
            }

            responseOfGroup.value = i;
            responseOfGroup.sequence = sequence;
        }

        return responseOfGroup;
    }




}
