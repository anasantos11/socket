package server;

public enum Response {
	ERROR_NAME_IN_USE("S - Este nome j� est� em uso"), CONNECTED_SUCCESS("S - Conex�o realizada com sucesso"), 
	UNKNOWN_COMMAND("S - Comando n�o reconhecido"), NOT_FOUND_CONNECTED_USER("S - N�o encontrado usu�rio conectado com este nome");

	private final String textMessage;

	Response(String message) {
		this.textMessage = message;
	}

	public String getValue() {
		return this.textMessage;
	}
	
	 public static Response findFromString(String text) {
		    for (Response r : Response.values()) {
		      if (r.textMessage.equalsIgnoreCase(text)) {
		        return r;
		      }
		    }
		    return null;
		  }

}