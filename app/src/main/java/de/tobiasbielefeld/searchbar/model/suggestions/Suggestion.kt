package de.tobiasbielefeld.searchbar;

/**
 * Suggestions "Interface"
 * For now this will be the only suggestions class. In the future, when
 * multiple search providers will be supported, this may need to be subclassed or
 * changed to an interface and implemented for each search provider.
 * TODO work out a better interface method than toString(). toString() is used bc that makes the listview adapter code super simple.
 */
class Suggestion(val phrase: String) {
	override fun toString() : String{
		return this.phrase
	}
}
