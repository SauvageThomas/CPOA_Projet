package app.command;

public class CommandFactory {

	public Command getCommandFromString(String commandLine) {
		String[] args = commandLine.split(" ");

		KeyWord keyWord;
		try {
			keyWord = KeyWord.valueOf(args[0]);
		} catch (java.lang.IllegalArgumentException e) {
			return null;
		}

		Argument arg = null;
		if (args.length > 1) {
			try {
				arg = Argument.valueOf(args[1]);
			} catch (java.lang.IllegalArgumentException e) {
			}
		}

		switch (args.length) {
		case 1:
			if (keyWord.getLen() == 1) {
				return new Command(keyWord);
			} else {
				return null;
			}
		case 2:
			if (keyWord.getLen() == 2) {
				return new Command(keyWord, args[1]);
			} else {
				return null;
			}
		case 3:

			if (keyWord.hasArgument()) {
				if (arg == null) {
					return null;
				}

				if (keyWord.getLen() == -1) {
					if (keyWord.getLenArg(arg) == 3) {
						return new Command(keyWord, arg, args[2]);
					} else {
						return null;
					}
				}

				if (keyWord.getLen() == 3) {
					return new Command(keyWord, arg, args[2]);
				} else {
					return null;
				}
			} else {
				if (keyWord.getLen() == 3) {
					return new Command(keyWord, args[1] + " " + args[2]);
				} else {
					return null;
				}
			}
		default:

			if (arg == null) {
				return null;
			}

			if (keyWord.getLen() == -1) {
				if (keyWord.getLenArg(arg) == 4) {
					return new Command(keyWord, arg,
							commandLine.split(" ", 3)[2]);
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}
}
