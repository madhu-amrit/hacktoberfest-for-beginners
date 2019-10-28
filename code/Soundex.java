package com.company;

import java.util.*;

public class Soundex {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[31mERROR  \033[0m");

        Database database = new Database();
        ArrayList<Document> documents = new ArrayList<>();
        documents.add(new Document(0, "Hahah I'm Harman"));
        documents.add(new Document(1, "HhH Harmann"));
        for (Document d: documents){
            String[] texts = d.getText().replaceAll("[^a-zA-Z ]", "").split(" ");
            int docId = d.getId();
            for (String text: texts){
                database.addTerm(soundex(text.toUpperCase()), new Appearance(docId, 1));
            }
        }
        System.out.println("Enter search query\n");
        String query = scanner.nextLine();
        searchQuery(soundex(query.toUpperCase()), database, documents);
    }

    public static void searchQuery(String query, Database db, ArrayList<Document> documents){
        Set<Integer> docIds = new HashSet<>();
        String[] queryBreakUp = query.split(" ");
        for (String s: queryBreakUp){
            Set<Integer> result = db.getTermAppearance(s);
            if (result != null)  docIds.addAll(result);
        }
        if (docIds.isEmpty()) System.out.println("Your query does not match any set of texts from the documents");
        else displayResults(docIds, documents, queryBreakUp);
    }

    public static void displayResults(Set<Integer> resultSet, ArrayList<Document> documents, String[] queries){
        Iterator iterator = resultSet.iterator();
        for (int i = 0; i < resultSet.size(); i++){
            while (iterator.hasNext()) {
                System.out.println(documents.get((Integer) iterator.next()).getText());
            }
        }
    }

    /**
     * Encodes the string based on the Soundex Algorithm
     * @param s
     */
    public static String soundex(String s){
        StringBuilder newString = new StringBuilder();
        newString.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++){
            newString.append(mapLetterToNumber(s.charAt(i)));
        }
        return removeConsecutive(newString.toString());
    }

    /**
     * Removes one out of each pair of consecutive identical digits
     * @param str
     */
    public static String removeConsecutive(String str){
        char[] st = str.toCharArray();
        int j = 0;

        // Traversing string
        for (int i=1; i<str.length(); i++)
        {
            // If current character S[i]
            // is different from S[j]
            if (st[j] != st[i])
            {
                j++;
                st[j] = st[i];
            }
        }
        j++;
        return removeZeros(st, j);
    }

    /**
     * Removes all zeros from the string
     * @param ch
     * @param len
     */
    public static String removeZeros(char[] ch, int len){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++){
            if (ch[i] != '0') stringBuilder.append(ch[i]);
        }
        return addTrailingZeros(stringBuilder.toString());
    }

    public static String addTrailingZeros(String sttr){
        if (sttr.length() < 4){
            for (int i = 0; i < 4-sttr.length(); i++)
                sttr = sttr + "0";
        } else if (sttr.length() > 4) return sttr.substring(0, 4);
        return sttr;
    }

    /**
     *
     * @param ch
     * @return
     */
    public static int mapLetterToNumber(char ch){
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('A', 0);
        map.put('E', 0);
        map.put('I', 0);
        map.put('O', 0);
        map.put('U', 0);
        map.put('W', 0);
        map.put('H', 0);
        map.put('Y', 0);
        map.put('B', 1);
        map.put('F', 1);
        map.put('P', 1);
        map.put('V', 1);
        map.put('D', 3);
        map.put('T', 3);
        map.put('L', 4);
        map.put('M', 5);
        map.put('N', 5);
        map.put('R', 6);
        map.put('C', 2);
        map.put('G', 2);
        map.put('J', 2);
        map.put('K', 2);
        map.put('Q', 2);
        map.put('S', 2);
        map.put('X', 2);
        map.put('Z', 2);
        return map.get(Character.toUpperCase(ch));
    }
}
