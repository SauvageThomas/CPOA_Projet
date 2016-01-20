package model;

import java.lang.reflect.Method;

import app.Function;

/**
 * This enum contains the differents keywords (the first word of the command)
 *
 */
public enum KeyWord {

	show {
		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("show");
		}
	},
	add {
		@Override
		public int getLenArg(Argument arg) {
			switch (arg) {
			case task:
				return 4;
			case project:
				return 3;
			default:
				return -1;
			}
		}

		@Override
		public boolean hasArgument() {
			return true;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			switch (arg) {
			case task:
				return Function.class.getMethod("addTask", String.class);
			case project:
				return Function.class.getMethod("addProject", String.class);
			}
			return null;

		}
	},
	remove {
		@Override
		public int getLen() {
			return 3;
		}

		public boolean hasArgument() {
			return true;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			switch (arg) {
			case task:
				return Function.class.getMethod("removeTask", String.class);
			case project:
				return Function.class.getMethod("removeProject", String.class);
			}
			return null;

		}
	},
	check {
		@Override
		public int getLen() {
			return 2;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("check", String.class);
		}
	},
	uncheck {
		@Override
		public int getLen() {
			return 2;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("uncheck", String.class);
		}
	},
	help {
		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("help");
		}
	},
	quit {
		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("quit");
		}
	},
	deadline {
		@Override
		public int getLen() {
			return 3;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("deadline", String.class);
		}
	},
	today {

		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public Method getAssociatedFunction(Argument arg)
				throws NoSuchMethodException, SecurityException {
			return Function.class.getMethod("today");
		}
	};

	/**
	 * This method return the number of words for a given KeyWord (excepted the
	 * keyWord add)
	 * 
	 * @return length of the command
	 */
	public int getLen() {
		return -1;
	}

	/**
	 * When the command got an argument (for the add commands), this method will
	 * return you the length of the command depends on the given argument (task
	 * or project)
	 * 
	 * @param arg
	 * @return length of the command
	 */
	public int getLenArg(Argument arg) {
		return -1;
	}

	/**
	 * 
	 * This method permits to know if the given command has arguments or not.
	 */
	public boolean hasArgument() {
		return false;
	}

	public abstract Method getAssociatedFunction(Argument arg)
			throws NoSuchMethodException, SecurityException;

}
