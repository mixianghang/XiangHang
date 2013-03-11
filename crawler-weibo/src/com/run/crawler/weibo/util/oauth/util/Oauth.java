package com.run.crawler.weibo.util.oauth.util;

public class Oauth implements OauthInterface {
	private String client_id;
	private String client_secret;
	private String redirect_uri;
	private String response_type;
	private String code;
	private String access_token;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Oauth(String client_id, String client_secret, String redirect_uri,
			String response_type, String code, String access_token) {
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.redirect_uri = redirect_uri;
		this.response_type = response_type;
		this.code = code;
		this.access_token = access_token;
	}

	public String oauthUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append("access_token=").append(access_token);
		return sb.toString();
	}

	public String getClient_id() {
		return client_id;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public String getResponse_type() {
		return response_type;
	}

	public String getCode() {
		return code;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
}