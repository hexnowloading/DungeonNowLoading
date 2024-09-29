package dev.hexnowloading.dungeonnowloading.entity.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveSet<T extends Object> {

    private int LOWEST_PRIORITY = 5;

    List<Move> moveSet = new ArrayList<>();

    private class Move {
        T object;
        int priority;
        int cooldown;
        int currentCooldownTick;
    }

    public void addMove(T object, int priority, int cooldown, int initialCooldown) {
        Move move = new Move();
        move.object = object;
        move.priority = priority;
        move.cooldown = cooldown;
        move.currentCooldownTick = initialCooldown;
        moveSet.add(move);
    }

    public void tick() {
        //System.out.println("Move Ticks: ---------------------------------------------------------");
        for (Move move : moveSet) {
            if (move.currentCooldownTick > 0) {
                move.currentCooldownTick--;
            }
            //System.out.println(move.object + " : " + move.currentCooldownTick);
        }
    }

    public T selectMove() {
        Move choosenMove = new Move();
        choosenMove.priority = LOWEST_PRIORITY + 1;
        int i = 0;
        ArrayList<Move> moveList = new ArrayList<>(moveSet.stream().filter(move -> move.currentCooldownTick == 0).toList());
        Collections.shuffle(moveList);
        for (Move move : moveList) {
            if (choosenMove.priority > move.priority) {
                choosenMove = move;
                i = moveSet.indexOf(move);
            }
        }
        moveSet.get(i).currentCooldownTick = moveSet.get(i).cooldown;
        return choosenMove.object;
    }

    public boolean isEmpty() {
        return moveSet.isEmpty();
    }

    public void clear() {
        moveSet.clear();
    }
}
