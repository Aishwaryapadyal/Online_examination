package online_examination;

import java.util.ArrayList;
import java.util.HashMap;

class User {
    private String username;
    private String password;
    private HashMap<Question, Integer> answers = new HashMap<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addAnswer(Question question, int answer) {
        answers.put(question, answer);
    }

    public int calculateScore(ArrayList<Question> questions) {
        int score = 0;
        for (Question question : questions) {
            if (answers.get(question) != null && answers.get(question) == question.getCorrectAnswerIndex()) {
                score++;
            }
        }
        return score;
    }

    public void updateProfile(String newUsername, String newPassword) {
        this.username = newUsername;
        this.password = newPassword;
    }
}