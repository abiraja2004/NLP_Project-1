package com.utd.Domain;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.utd.SentenceSimilarity.ComputeSentenceSimilarity;

public class Cluster {

	public int clusterId;
	public String centroidSentence;
	public Map<String, Integer> mapClusterSentence;
	public Map<String, Double> mapClusterSSscore;
	
	public Cluster() {
		clusterId = 0;
		mapClusterSentence = new HashMap<String, Integer>();
		mapClusterSSscore = new HashMap<String, Double>();
	}
	
	public void addSentenceToCluster(String sentence, Integer originalIndex) {
		if(!mapClusterSentence.containsKey(sentence))
			mapClusterSentence.put(sentence, originalIndex);
	}
	
	public void computeIntraClusterSimilarityScore() throws Exception {
		
		String str1, str2;
		Integer index1, index2;
		Double similarityScore;
		for(Map.Entry<String, Integer> entry1 : mapClusterSentence.entrySet()) {
			str1 = entry1.getKey();
			index1 = entry1.getValue();
			for(Map.Entry<String, Integer> entry2 : mapClusterSentence.entrySet()) {
				str2 = entry2.getKey();
				index2 = entry2.getValue();
				if(mapClusterSSscore.containsKey(index2+"-"+index1) || index1 == index2)
					continue;

				similarityScore = ComputeSentenceSimilarity.ComputeSimilarity(str1, str2);	

				if(!mapClusterSSscore.containsKey(index1+"-"+index2)) {
					mapClusterSSscore.put(new String(index1+"-"+index2), similarityScore);
//					System.out.println("Cluster" + clusterId + ": ("+index1+"-"+index2+") = " + similarityScore);
				}
				else
					System.out.println("\n\n\n>>>>>>>>>>> THIS SHOULD NOT BE PRINTED <<<<<<<<<<<<\n\n\n\n");
					
			}
		}
	}

	public void recomputeNewCentroid() {
		// split keys and add to a map
		Map<Integer, Double> mapSentenceMaxScore = new HashMap<Integer, Double>();
		String tempIndex[] = new String[2]; int tempIndex1;
		for(Map.Entry<String, Double> entry : mapClusterSSscore.entrySet()) {
			tempIndex = entry.getKey().split("-");
//			System.out.println("Split value: " + entry.getKey() + " >> " + entry.getValue());
			for(int i=0;i<tempIndex.length;i++) {
				tempIndex1 = Integer.parseInt(tempIndex[i]);
				if(mapSentenceMaxScore.containsKey(tempIndex1))
					mapSentenceMaxScore.put((tempIndex1), mapSentenceMaxScore.get(tempIndex1)+entry.getValue());
				else
					mapSentenceMaxScore.put((tempIndex1), entry.getValue());
			}
		}
		// find the maximum score
		double maxValue = Double.MIN_VALUE; int newCentroidIndex =0;
		for(Map.Entry<Integer, Double> entry : mapSentenceMaxScore.entrySet()) {
//			System.out.println("Sentence " + entry.getKey() + ": " + entry.getValue());
			if(maxValue < entry.getValue()) {
				maxValue = entry.getValue();
				newCentroidIndex = entry.getKey();
			}
		}
		// extract the new centroid sentence
		for(Map.Entry<String, Integer> entry : mapClusterSentence.entrySet())
			if(entry.getValue() == newCentroidIndex) {
				this.centroidSentence = entry.getKey();
//				System.out.println("The new centroid is: " + this.centroidSentence);		
			}
	}
}
