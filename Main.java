package Conversão;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        String[] classesDeOrdenacao = {
            "Conversão.HeapSortConquista",
            "Conversão.InsertionConquista",
            "Conversão.MergeSortConquista",
            "Conversão.QuickSortConquista",
            "Conversão.SelectionSortConquista",
            "Conversão.CountingSortPrice",
            "Conversão.HeapPrice",
            "Conversão.InsertionPrice",
            "Conversão.MergePrice",
            "Conversão.QuickSortPrice",
            "Conversão.QuickSortPriceMD3",
            "Conversão.SelectionPrice"
        
        };

        for (String nomeClasse : classesDeOrdenacao) {
            try {
                System.out.println("Executando " + nomeClasse);
                Class<?> clazz = Class.forName(nomeClasse);
                Method metodoMain = clazz.getDeclaredMethod("main", String[].class);
                String[] params = null; 
                metodoMain.invoke(null, (Object) params);
            } catch (ClassNotFoundException e) {
                System.err.println("Classe não encontrada: " + nomeClasse);
            } catch (NoSuchMethodException e) {
                System.err.println("Método não encontrado: " + nomeClasse);
            } catch (Exception e) {
                System.err.println("Erro ao executar o algoritmo " + nomeClasse);
                e.printStackTrace();
            }
        }
    }
}




