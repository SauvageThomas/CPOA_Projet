package app.command;

public enum KeyWord {

	show {
		@Override
		public int getLen() {
			return 1;
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
	},
	remove {
		@Override
		public int getLen() {
			return 3;
		}
		
		public boolean hasArgument() {
			return true;
		}
	},
	check {
		@Override
		public int getLen() {
			return 2;
		}
	},
	uncheck {
		@Override
		public int getLen() {
			return 2;
		}
	},
	help {
		@Override
		public int getLen() {
			return 1;
		}
	},
	quit {
		@Override
		public int getLen() {
			return 1;
		}
	},
	deadline {
		@Override
		public int getLen() {
			return 3;
		}
	},
	today {
		@Override
		public int getLen() {
			return 1;
		}
	};

	public int getLen() {
		return -1;
	}

	public int getLenArg(Argument arg) {
		return -1;
	}
	public boolean hasArgument() {
		return false;
	}
}
