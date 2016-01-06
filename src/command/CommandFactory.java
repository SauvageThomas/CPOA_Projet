package command;

public class CommandFactory {

	Command command;

	public CommandFactory() {
	}

	public Command getCommandFromString(String commandLine) {

		String[] args = commandLine.split(" ", 2);

		KeyWord keyWord;
		try {
			keyWord = KeyWord.valueOf(args[0]);
		} catch (java.lang.IllegalArgumentException e) {
			System.out.println("Non valide");
			return null;
		}

		// Si mots clé unique (help, show)
		if (args.length == 1) {
			System.out.println("help/show");
			if (keyWord.equals(KeyWord.help) || keyWord.equals(KeyWord.show)) {
				return new Command(keyWord);
			}
			return null;
		}

		String[] tmp = args[1].split(" ", 2);

		Argument arg;
		try {
			arg = Argument.valueOf(tmp[0]);
		} catch (java.lang.IllegalArgumentException e) {

			try {
				Argument.valueOf(args[0]);
			} catch (java.lang.IllegalArgumentException r) {

				return new Command(keyWord, tmp[0]);
			}

			// Si mot clé + string (check <task>, uncheck <task>)
			return null;
		}

		if (tmp.length == 1) {
			return null;
		}

		if (Argument.valueOf(tmp[0]).equals(Argument.task)
				&& tmp[1].split(" ").length == 1) {
			return null;
		}
		// Sinon (add, remove)

		System.out.println(tmp[1]);
		System.out.println("add");
		return new Command(keyWord, arg, tmp[1]);
	}
}
