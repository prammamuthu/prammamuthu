//package com.kumar.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateChange 
{
	int[] availableCoins = {5, 2, 1,10,50,100};
	
	public static void main(String[] args)
	{
		int itemPrice = 90;
		int amountGiven = 100;
		CalculateChange calculateChange = new CalculateChange();
		
		Map<Integer, Integer> change = calculateChange.issueChange(itemPrice, amountGiven);
		
		for(int key : change.keySet())
		{
			System.out.println("Rs. " + key + " coin - " + change.get(key));
		}
		
		System.out.println("Done");
	}
	
	/**
	 * This one takes the price of the item as the parameter and the amount given by the customer
	 * as parameters
	 * 
	 * Return Rs. 8 in multiple denominations that are available.
	 *  Rs 2 coins - 3
	 *  Rs 1 coin - 2
	 *  
	 *  (or)
	 *
	 *  Rs. 5 coin - 1
	 *  Rs. 2 coin - 1
	 *  Rs. 1 coin - 1
	 * 
	 * @param itemPrice - ex: Rs. 2
	 * @param amountGiven - ex: Rs. 10
	 * @return
	 */
	private Map<Integer, Integer> issueChange(int itemPrice, int amountGiven)
	{
		Map<Integer, Integer> change = new HashMap<Integer, Integer>();
		
		//1. calculate the balance amount
		int balanceAmount  = amountGiven - itemPrice;
		
		//2. Calculate all possible change combinations
		
		List<List<Integer>> changeOptions = getAllPossibleChangeCombinations(balanceAmount);
		
		System.out.println("Change options:"+ changeOptions);
		
		printChangeOptions(changeOptions);
		
		//3. Pick the optimized change (optimized = less number of coins)
		
		
		List<Integer> selectedOption = pickOptimizedCoins(changeOptions);
		
		for(Integer selectedCoin : selectedOption)
		{
			if(change.containsKey(selectedCoin))
			{
				int count = change.get(selectedCoin);
				++count;
				change.put(selectedCoin, count);
			} else
			{
				change.put(selectedCoin, 1);
			}
		}
		
		return change;
	}
	
	private List<Integer> pickOptimizedCoins(List<List<Integer>> changeOptions)
	{
		int maxCoins = 10000;
		
		List<Integer> selectedOption = null;
		
		for(List<Integer> changeOption : changeOptions)
		{
			if(changeOption.size() < maxCoins)
			{
				maxCoins = changeOption.size();
				selectedOption = changeOption;
			}
		}
		
		return selectedOption;
	}
	
	private void printChangeOptions(List<List<Integer>> changeOptions)
	{
		for(List<Integer> changeOption : changeOptions)
		{
			int total = 0;
			for(Integer change : changeOption)
			{
				total += change;
				
				System.out.print(change + ",");
			}
			
			System.out.println(" = " + total);
		}
	}
	
	/**
	 * Input to change amount .. Rs. 8 (balance amount)
	 * return
	 * {
	 * 	{5, 2, 1},
	 * 	{5, 1, 1, 1},
	 * 	{2, 2, 2, 2}
	 * }
	 * @param changeAmount
	 * @return
	 */
	private List<List<Integer>> getAllPossibleChangeCombinations(int changeAmount)
	{
		List<List<Integer>> changeOptions = new ArrayList<List<Integer>>();
		
		for(int i = 0; i < availableCoins.length; i++)
		{
			//initially = 5
			int coinAmount = availableCoins[i]; //
			
			int remainingAmount = changeAmount - coinAmount; // 3

			System.out.println("Change amount:" + changeAmount + " : Coin amount: " + coinAmount + " : remaining amount:"+ remainingAmount);

			if(remainingAmount > 0)
			{
				List<List<Integer>> remainingAmountChangeOptions = getAllPossibleChangeCombinations(remainingAmount);
			
				//for option 5 (first coin)..change options will
			
				for(List<Integer> remainingAmountOption : remainingAmountChangeOptions)
				{
					remainingAmountOption.add(0,  coinAmount); //5 is prefixed with the change options from remaining amount 3
					//this list will contain something like - 5, 2, 1
					//this list will contain something like - 5, 1, 2
					//this list will contain something like - 5, 1, 1, 1
				
					changeOptions.add(remainingAmountOption);
				}
			} else if(remainingAmount == 0)
			{
				List<Integer> changeOption = new ArrayList<Integer>();
				changeOption.add(coinAmount);
				changeOptions.add(changeOption);
			}
		}
		
		return changeOptions;
	}
}
