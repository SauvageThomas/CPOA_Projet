package app.command;

import java.lang.reflect.InvocationTargetException;

import model.Argument;
import model.KeyWord;

public class Command {

	@Override
	public String toString() {
		return keyWord.toString() + " " + arg.toString() + " "
				+ parameter.toString();
	}

	private KeyWord keyWord;
	private Argument arg;
	private String parameter;

	/**
	 * The Command constructor simply permits to create a Command with only one
	 * word (keyword).
	 * 
	 * @param keyWord
	 */
	Command(KeyWord keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * The Command constructor simply permits to create a Command with a
	 * keyword, an argument, and parameters.
	 * 
	 * @param keyWord
	 * @param arg
	 * @param parameter
	 */
	Command(KeyWord keyWord, Argument arg, String parameter) {
		this.keyWord = keyWord;
		this.arg = arg;
		this.parameter = parameter;
	}

	/**
	 * The Command constructor simply permits to create a Command with a keyword
	 * and parameters.
	 * 
	 * @param keyWord
	 * @param parameter
	 */
	Command(KeyWord keyWord, String parameter) {
		this.keyWord = keyWord;
		this.parameter = parameter;
	}

	/**
	 * This getter return the first word of the command
	 * 
	 * @return keyword
	 */
	public KeyWord getKeyWord() {
		return keyWord;
	}

	/**
	 * This getter return the second word of a command ( project or task)
	 * 
	 * @return arg
	 */
	public Argument getArg() {
		return arg;
	}

	/**
	 * This getter return the last part of a command (words excepted the
	 * keywords)
	 * 
	 * @return parameter
	 */
	public String getParameter() {
		return parameter;
	}

	public void execute() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (parameter == null) {
			keyWord.getAssociatedFunction(arg).invoke(null);
		} else {
			keyWord.getAssociatedFunction(arg).invoke(null, parameter);
		}
	}

}
