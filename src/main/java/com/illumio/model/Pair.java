package com.illumio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T1, T2> {
    public T1 fst;
    public T2 snd;

    public static Pair of(Object fst, Object snd) {
        return new Pair<>(fst, snd);
    }
}
