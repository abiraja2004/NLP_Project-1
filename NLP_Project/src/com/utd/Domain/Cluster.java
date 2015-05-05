package com.utd.Domain;

import java.util.HashMap;
import java.util.Map;

import com.utd.SentenceSimilarity.ComputeSentenceSimilarity;

public class Cluster {

	public String centroidSentence;
	public Map<String, Integer> mapClusterSentence;
	public Map<String, Double> mapClusterSSscore;
	
	public Cluster() {
		mapClusterSentence = new HashMap<String, Integer>();
		mapClusterSSscore = new HashMap<String, Double>();
	}
	
	public void addSentenceToCluster(String sentence, Integer originalIndex) {
		if(!mapClusterSentence.containsKey(sentence))
			mapClusterSentence.put(sentence, originalIndex);
	}
	
	public void computeIntraSimilarityScore() throws Exception {
		
		String str1, str2;
		Integer index1, index2;
		Double similarityScore;
		for(Map.Entry<String, Integer> entry1 : mapClusterSentence.entrySet()) {
			str1 = entry1.getKey();
			index1 = entry1.getValue();
			for(Map.Entry<String, Integer> entry2 : mapClusterSentence.entrySet()) {
				str2 = entry2.getKey();
				index2 = entry2.getValue();
				if(index1 == index2)
					continue;
				do {
					similarityScore = ComputeSentenceSimilarity.ComputeSimilarity(str1, str2);	
				}while(similarityScore != null);
				
				if(!mapClusterSSscore.containsKey(index1+"-"+index2)) 
				{
					mapClusterSSscore.put(new String(index1+"-"+index2), similarityScore);
					System.out.println("Score: ("+index1+"-"+index2+")" + similarityScore);
				}
					
				else
					System.out.println("\n\n\n>>>>>>>>>>> THIS SHOULD NOT BE PRINTED <<<<<<<<<<<<\n\n\n\n");
					
			}
		}
		
	}

	public void recomputeNewCentroid() {
		
	}
}
