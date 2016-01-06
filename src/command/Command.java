package command;


public class Command {

	private KeyWord keyWord;
	private Argument arg;

	public Command(KeyWord keyWord) {
		this.keyWord = keyWord;
	}

	public Command(KeyWord keyWord, Argument arg, String name) {
		this.keyWord = keyWord;
	}

	public Command(KeyWord keyWord, String name) {
		this.keyWord = keyWord;
	}
}
