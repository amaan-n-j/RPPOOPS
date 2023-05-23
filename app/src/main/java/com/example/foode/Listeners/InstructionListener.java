package com.example.foode.Listeners;

import com.example.foode.Models.InstructionResponse;

import java.util.List;

public interface InstructionListener {
    void didFetch(List<InstructionResponse> response, String message);
    void didError(String message);
}
