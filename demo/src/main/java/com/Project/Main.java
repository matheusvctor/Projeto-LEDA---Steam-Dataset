package com.Project;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        String[] classesDeOrdenacao = {
            "Formatacao.ProjectUpgrade.HashMapOrdenacao",
            "Formatacao.ProjectUpgrade.FiltrarParaLinux",
            "Formatacao.ProjectUpgrade.FormatacaoData",
            "Formatacao.ProjectUpgrade.JogosQueSuportamPortugues",
            "Ordenacao.CountingSortPrice",
            "Ordenacao.HeapPrice",
            "Ordenacao.HeapSortConquista",
            "Ordenacao.InsertionConquista",
            "Ordenacao.InsertionPrice",
            "Ordenacao.MergePrice",
            "Ordenacao.MergeSortConquista",
            "Ordenacao.QuickSortConquista",
            "Ordenacao.QuickSortPrice",
            "Ordenacao.QuickSortPriceMD3",
            "Ordenacao.SelectionPrice",
            "Ordenacao.SelectionSortConquista"
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
