package com.example.myapplication.models;

/** @noinspection unused, unused */
public class GeminiResponse {
    private Candidate[] candidates;

    public String getText() {
        if (candidates != null && candidates.length > 0) {
            return candidates[0].output;
        }
        return "No response from AI.";
    }

    /** @noinspection unused*/
    private static class Candidate {
        private String output;
    }
}

