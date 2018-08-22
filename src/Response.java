
public enum Response {
	ERROR_CONNECTION(
			"S - N�o foi poss�vel prosseguir com a conex�o ao servidor. Tente novamente usando o comando CONNECT:SEU_NOME_AQUI"), ERROR_NAME_IN_USE(
					"S - N�o foi poss�vel prosseguir com a conex�o ao servidor. Este nome j� est� em uso, tente novamente"), CONNECTED_SUCCESS(
							"S - Conex�o realizada com sucesso"), UNKNOWN_COMMAND(
									"S - Comando n�o reconhecido"), NOT_FOUND_CONNECTED_USER(
											"S - N�o foi encontrado usu�rio conectado com este nome"), NOT_INFORMED_USER_TO_SEND_MESSAGE(
													"S - N�o foi informado o usu�rio que deseja enviar a mensagem direta"), NOT_INFORMED_MESSAGE(
															"S - N�o foi informado a mensagem a ser enviada");

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
