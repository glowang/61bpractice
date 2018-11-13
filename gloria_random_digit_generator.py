'''
Ideation: 
- Objective 
	To use the different ways of arranging 52 cards to represent unique results of 128-bit random value
- Approach:
	1. Generate 52! possible card arrangments. 
	2. Use the Lehmer rule to turn each of the 52! permutations into a unique decimal. 
	3. Convert each decimal representation of the permutation into binary representation.   
- Caveat: 
	1. there are more ways of arranging 52 cards than unique 128-bit sequence (52! > 2**128).
	2. Each unique 128-bit sequence is supposed to be represented by ( n = 52! / 2**128) card arrangments, 
	but n has a remainder, which means that some sequences will correspond with more card arrangments than others. 
	However, because 52! is significantly larger than 2**128, I believe that the inequality of probability distribution
	among all possible 128-bit sequences is neglible. 

'''

def generate_cards():
	cards = ['A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K']
	suits = ['d', 'h', 's', 'c']
	deck = []
	
	for suit in suits:
		for card in cards:
			deck.append(card + suit)

	random.shuffle(deck)
	return deck

def create_mapping(cards):
	"""
	Create a mapping of cards to integers 0-51 according to some initailizer deck. 
	Should be called once in order to assign integer values randomly to cards.
	This is because Lehmer code takes permutations of numbers, not symbols.
	"""
	mapping = {}
	for i in range(52):
		mapping[cards[i]] = i
	
	return mapping

def card_to_number(deck, mapping):
	"""
	Converts a provided deck to integer values based on some mapping.
	"""
	nums =  [mapping[card] for card in deck]
	#print(nums)
	#print(deck)
	return nums

def factorial(n): 
	"""
	Helper factorial method since we can't import math
	"""  
	if n==0: 
		return 1
	else:
		return n*factorial(n-1) 

def factoradic(lst): 
	"""
	Use the Lehmer Code to encode a permutation to a list of factoradics.
	Source: https://www.researchgate.net/figure/The-Lehmer-code-A-complete-translation-from-permutation-to-decimal-by-way-of-the_fig1_230831447
	"""
	factoradic_accumulator = []

	while len(lst) > 0:
		mn = min(lst)
		num_gr = 0
		for i in lst[:lst.index(mn)]:
			if i > mn:
				num_gr += 1
		lst.remove(mn)
		factoradic_accumulator.append(num_gr)
	#print(factoradic_accumulator)
	return factoradic_accumulator


def factoradic_to_decimal(fact): 
	"""
	Convert factoradics to decimal (e.g. [2, 2, 0, 0] -> 16)
	"""
	dec = 0
	for i in range(len(fact)):
		dec += factorial(i) * fact[len(fact) - 1 - i]
	#print(dec)
	return dec 

def decimal_to_binary(num):
	"""
	Finally, convert the unique decimal identifier we obtained into a binary string.
	"""
	coefficients = "" 
	while num != 0:
		coefficients += str(num % 2)
		num = num // 2
	return coefficients

def cards_to_binary(deck, mapping):
	"""
	Algorithm to convert an ordering of cards to 128-bit binary string
	"""
	nums = card_to_number(deck, mapping)
	fact = factoradic(nums)
	dec = factoradic_to_decimal(fact)
	bi = decimal_to_binary(dec)
	return bi[:-128] # we return the least significant 128 bits of the number


	
