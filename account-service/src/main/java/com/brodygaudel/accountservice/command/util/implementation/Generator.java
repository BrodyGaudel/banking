package com.brodygaudel.accountservice.command.util.implementation;

import com.brodygaudel.accountservice.command.util.IdGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class Generator implements IdGenerator {

    private final CounterRepository counterRepository;

    public Generator(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }


    @Override
    public String autoGenerate() {
        Counter counter = counterRepository.save(new Counter());
        String id = String.format("%16d",counter.getId());
        return reverse(id);
    }

    public static @NotNull String reverse(@NotNull String input) {
        StringBuilder reversed = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed.append(input.charAt(i));
        }
        return reversed.toString();
    }

}
