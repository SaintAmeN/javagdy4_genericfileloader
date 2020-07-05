package com.sda.javagdy4.genericquestionnaire;

import com.sda.javagdy4.genericquestionnaire.exceptions.EndOfAnswerIdentifiers;
import com.sda.javagdy4.genericquestionnaire.exceptions.LoaderException;
import com.sda.javagdy4.genericquestionnaire.model.Answer;
import com.sda.javagdy4.genericquestionnaire.model.Question;
import com.sda.javagdy4.genericquestionnaire.model.Questionnaire;
import lombok.extern.log4j.Log4j;

import java.util.*;

@Log4j
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String command;

        do {
            log.info("Please enter command [listQuestions]:");
            command = scanner.nextLine();

            if (command.startsWith("listQuestions")) {
                handleLoadAndPrintQuestions();
            } else if (command.startsWith("addQuestion")) {
                handleAddQuestion(command);
            } else if (command.equalsIgnoreCase("questionnaire")) {
                handleQuestionnaire(scanner);
            }

        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleQuestionnaire(Scanner scanner) {
        FileLoader<Question> questionFileLoader = new FileLoader<>(Question.class);
        Map<Long, Question> questionMap = questionFileLoader.load();

        List<Answer> answerList = new ArrayList<>();
        for (Map.Entry<Long, Question> longQuestionEntry : questionMap.entrySet()) {                // dla każdego pytania z kolekcji
            log.info("Question: " + longQuestionEntry.getValue());                      //zadajemy pytanie
            String answerString = scanner.nextLine();                                                //użytkownik udziela odp.

            Answer answer = new Answer(String.valueOf(longQuestionEntry.getKey()), answerString);
            answerList.add(answer);
        }
        Questionnaire questionnaire = new Questionnaire(answerList);

        FileLoader<Questionnaire> answerFileLoader = new FileLoader<>(Questionnaire.class);
        Map<Long, Questionnaire> answerMap;
        try {
            answerMap = answerFileLoader.load();            // jeśli się da (jest plik) to załadujemy mape
        }catch (LoaderException le ){
            answerMap = new HashMap<>();                    // jeśli nie ma pliku, to stworzymy nową mapę
        }
        answerMap.put(findNextFreeIdentifier(answerMap.keySet()), questionnaire);

        answerFileLoader.save(answerMap);
    }

    private static Long findNextFreeIdentifier(Set<Long> keySet) {
        for (Long i = 0L; i < 1000L; i++) {
            if(!keySet.contains(i)){ // jeśli w zbiorze nie ma wartości I, to zwróć ją
                return i;
            }
        }
        throw new EndOfAnswerIdentifiers("Skonczyly sie identyfikatory :( ");
    }

    private static void handleAddQuestion(String command) {
        FileLoader<Question> questionFileLoader = new FileLoader<>(Question.class);
        // addQuestion 50 Czy to zadanie jest trudne?
        String[] words = command.split(" ", 3);
        Long questionIdentifier = Long.valueOf(words[1]);
        String questionContent = words[2];

        Question question = new Question(questionContent);      // nowe pytanie

        // załadowanie istniejących pytań ( z pliku )
        Map<Long, Question> questionsFromFile = questionFileLoader.load();
        questionsFromFile.put(questionIdentifier, question);

        questionFileLoader.save(questionsFromFile); // zapis starych pytań + nowego pytania
    }

    private static void handleLoadAndPrintQuestions() {
        FileLoader<Question> questionFileLoader = new FileLoader<>(Question.class);
        Map<Long, Question> questionMap = questionFileLoader.load();

        // map.values()
        // map.keySet()
        // map.entrySet()
        for (Map.Entry<Long, Question> longQuestionEntry : questionMap.entrySet()) {
            log.info(longQuestionEntry.getKey() + " -> " + longQuestionEntry.getValue());
        }
    }
}
