package app.command;

public class Command {

	@Override
	public String toString() {
		return keyWord.toString() + " " + arg.toString() + " "
				+ parameter.toString();
	}

	private KeyWord keyWord;
	private Argument arg;
	private String parameter;

	Command(KeyWord keyWord) {
		this.setKeyWord(keyWord);
	}

	Command(KeyWord keyWord, Argument arg, String parameter) {
		this.setKeyWord(keyWord);
		this.arg = arg;
		this.parameter = parameter;
	}

	Command(KeyWord keyWord, String parameter) {
		this.setKeyWord(keyWord);
		this.parameter = parameter;
	}

	public KeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(KeyWord keyWord) {
		this.keyWord = keyWord;
	}

	public Argument getArg() {
		return arg;
	}

	public void setArg(Argument arg) {
		this.arg = arg;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
