package com.utd.Domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.utd.SentenceSimilarity.ComputeSentenceSimilarity;

public class Sentence {

	public Map<String, Integer> mapSentence;
	public Map<String, Double> mapSSscore;
	public Integer kPoints[] = {1,5,10};
	public ArrayList<Cluster> clustersArray;
	
	public Sentence() {
		 mapSentence = new HashMap<String, Integer>();
		 mapSSscore = new HashMap<String, Double>();
		 clustersArray = new ArrayList<Cluster>(kPoints.length);
		 for(int i=0;i<kPoints.length;i++)
			 clustersArray.add(new Cluster());
	}
	
	public void addSentence(String sentence) {
		if(!mapSentence.containsKey(sentence))			
			mapSentence.put(sentence, mapSentence.size()+1);
	}

	public void testFunction() {
		System.out.println("mapSentence size: " + mapSentence.size());
		for(Cluster clusterObj : clustersArray) {
			System.out.println("Cluster centroid value = " + clusterObj.centroidSentence);
		}
	}
	

	/**
	 * Function that chooses 'k' random sentences as centroid of each cluster 
	 */
	public void chooseKRandomPoints() {
		kPoints[0] = 1;								// customized the values of k
		kPoints[1] = 3;
		kPoints[2] = 7;
		int clusterIndex = 0;
		System.out.println("asdf" + clustersArray.size());
		for(Map.Entry<String, Integer> entry : mapSentence.entrySet()) {
			if(isValueInK(entry.getValue(), kPoints))
				clustersArray.get(clusterIndex++).centroidSentence = entry.getKey();
		}
	}

	public void classifyAllSentencesToClusters() throws Exception {
		int clusterIndex = 0; double max, score;
		for(Map.Entry<String, Integer> entry : mapSentence.entrySet()) {
			max = Integer.MIN_VALUE;
			for(int i=0;i<clustersArray.size();i++) {
				score = ComputeSentenceSimilarity.ComputeSimilarity(entry.getKey(), clustersArray.get(i).centroidSentence);
				if(max < score) {
					max = score;
					clusterIndex = i;
				}
			}
			clustersArray.get(clusterIndex).addSentenceToCluster(entry.getKey(), entry.getValue());		// add to cluster
		}
	}

	public void runKMeansAlgorithm() throws Exception {
		
		for(int i=0;i<clustersArray.size();i++) {			// for each cluster
			clustersArray.get(i).computeIntraSimilarityScore();
			clustersArray.get(i).recomputeNewCentroid();
		}
		
	}

	/*
	 * Helper functions below
	 */
	
	/**
	 * Function that checks if the given point is in 'k' point array
	 * @param value
	 * @param kPoints
	 * @return
	 */
	private boolean isValueInK(Integer value, Integer[] kPoints) {
		for(int i=0;i<kPoints.length;i++)
			if(kPoints[i] == value)
				return true;
 		return false;
	}
	
	/**
	 * Customized function
	 * @param sentence
	 * @throws IOException 
	 */
	public void writeAllSentencesToAFile(Sentence sentence) throws IOException {
		
		// write all sentences
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Corpus/HashMap1.txt")));
		for(Map.Entry<String, Integer> entry : mapSentence.entrySet())
			bw.write(entry.getValue() + " >>> " + entry.getKey() + "\n");
		bw.close();
		
		// write all sentences of each cluster
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(new File("Corpus/AllClusterSentences")));
		for(int i=0;i<clustersArray.size();i++)
			bw1.write("Cluster" + i + " size: " + clustersArray.get(i).mapClusterSentence.size() + "\n");
		for(int i=0;i<clustersArray.size();i++) {
			bw1.write("\n\n\nCluster " + i + " sentences:\n\n");
			for(Map.Entry<String, Integer> entry : clustersArray.get(i).mapClusterSentence.entrySet()) 
				bw1.write(entry.getKey() + "\n");
		}
		bw1.close();
		
		// write the intra computational score
		bw1 = new BufferedWriter(new FileWriter(new File("Corpus/IntraClusterSimilarityScore.txt")));
		for(int i=0;i<clustersArray.size();i++) {
			bw1.write("\n\n\nCluster " + i + " intra computational scores:\n\n");
			for(Map.Entry<String, Double> entry : clustersArray.get(i).mapClusterSSscore.entrySet())
				bw.write(entry.getKey() + " >>> " + entry.getValue());
		}
			
	}
	
}
