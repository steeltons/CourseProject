package com.jenjetsu.course.utils;

import java.util.*;

public class RaitaAlgorithm implements StringSearchDriver{

    private Map<Character, Integer> charsMovesMap;
    private final String pattern;
    List<Integer> indexes;

    private RaitaAlgorithm(){
        pattern="";
        charsMovesMap = new HashMap<>();
    }

    private RaitaAlgorithm(String pattern){
        this.pattern = pattern;
        charsMovesMap = new HashMap<>();
        this.indexes = createIndexesList(pattern);
    }

    public static StringSearchDriver Driver(String pattern){
        return new RaitaAlgorithm(pattern);
    }

    public String getPattern(){
        return pattern;
    }

    @Override
    public List<Integer> search(String text, boolean uppercaseRequest) {
        if(text.equals("") || pattern.equals("") || pattern.length() > text.length())
            return Arrays.asList();
        String copyPattern = pattern;
        if(!uppercaseRequest){
            text = text.toLowerCase(Locale.ROOT);
            copyPattern = copyPattern.toLowerCase(Locale.ROOT);
        }
        int pLength = copyPattern.length();
        List<Integer> foundIndexes = new LinkedList<Integer>();

        for(int i = 1; i < pLength; i++){
            char temp = copyPattern.charAt(pLength - i - 1);
            if(!charsMovesMap.containsKey(temp))
                charsMovesMap.put(temp, i);
        }
        if(!charsMovesMap.containsKey(copyPattern.charAt(pLength - 1)))
            charsMovesMap.put(copyPattern.charAt(pLength - 1), pLength);

        int lastIndex = copyPattern.length() - 1;
        int firstIndex = 0;
        final int pMed = ((pLength % 2 == 0) ? pLength / 2 - 1 : pLength / 2);
        while (lastIndex < text.length()){
            int med = (lastIndex + firstIndex) / 2;
            int move = 0;
            if(text.charAt(lastIndex) == copyPattern.charAt(pLength - 1)
                && text.charAt(firstIndex) == copyPattern.charAt(0)
                && text.charAt(med) == copyPattern.charAt(pMed)
            )
            {
                if(compareStrings(text.substring(lastIndex - pLength + 1, lastIndex + 1), copyPattern)) {
                    foundIndexes.add(lastIndex - pLength + 1);
                    move = pLength;
                }
            }
            if(charsMovesMap.containsKey(text.charAt(lastIndex)))
                move = charsMovesMap.get(text.charAt(lastIndex));
            else
                move = copyPattern.length();
            firstIndex+= move;
            lastIndex+= move;
        }
        return foundIndexes;
    }

    private boolean compareStrings(String s1, String s2){
        for(Integer i : indexes)
            if(s1.charAt(i) != s2.charAt(i))
                return false;
        return true;
    }

    private List<Integer> createIndexesList(String pattern){
        List<Integer> temp = new ArrayList<>();
        for(int i = 0; i < pattern.length(); i++){
            temp.add(i);
        }
        temp.removeAll(Arrays.asList(0,pattern.length() - 1, pattern.length() / 2));
        return temp;
    }
}
