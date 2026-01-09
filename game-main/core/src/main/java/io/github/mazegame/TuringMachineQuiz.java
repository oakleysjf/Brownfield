package io.github.mazegame;

/**
 * Manages the Turing machine quiz question and answers.
 */
public final class TuringMachineQuiz {

    public static final String QUESTION = "Who is the inventor of the Turing\n machine?";
    
    public static final String[] ANSWERS = {
        "Ada Lovelace",
        "Alan Turing",
        "Charles Babbage"
    };
    
    public static final int CORRECT_ANSWER = 1; // Index of correct answer (Alan Turing)

    /**
     * Checks if the given answer index is correct.
     * 
     * @param answerIndex the index of the selected answer
     * @return true if the answer is correct, false otherwise
     */
    public static boolean isCorrect(int answerIndex) {
        return answerIndex == CORRECT_ANSWER;
    }
}
