package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Map.Entry;


import entities.Sale;

public class App {
    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Sale(Integer.parseInt( fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
                line = br.readLine();
            }

            HashSet<String> listSellers = new HashSet<String>();  

            for(Sale s : list) {
                listSellers.add(s.getSeller());
            }

            HashMap<String, Double> listTotal = new HashMap<>();

            double sum;
            for (String s : listSellers) {
                sum = list.stream()
                .filter(x -> x.getSeller().equals(s))
                .map(x -> x.getTotal())
                .reduce(0.0, (x,y) -> x + y);
                listTotal.put(s, sum);
            }

            for (Entry<String, Double> s : listTotal.entrySet()) {
				System.out.print(s.getKey() + " - R$ ");
				System.out.println(String.format("%.2f", s.getValue()));
			}   

        } catch (IOException e) {
            System.out.println("Erro: " + path + "(O sistema não pode encontrar o arquivo especificado)");
        }
        sc.close();
    }
}
