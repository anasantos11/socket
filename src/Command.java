

public enum Command {
	CONNECT("connect"), MESSAGE_DIRECT("message_direct"), MESSAGE_FOR_ALL("message_for_all"), DISCONNECT(
			"disconnect"), LIST_ALL_CONNECTED("list_all_connected");

	private final String text;

	Command(String message) {
		this.text = message;
	}

	public String getValue() {
		return this.text;
	}

	public static Command getCommand(String text) {
		for (Command c : Command.values()) {
			if (c.text.equalsIgnoreCase(text)) {
				return c;
			}
		}
		return null;
	}

}
