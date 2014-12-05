package com.noelmace.android.demo.littlenote.entitties;

public class Note {
	
	/**
	 * identifiant unique de la note
	 * (null si transient)
	 */
	private Long id = null;
	
	private String title;
	
	private String content;

	/**
	 * constructeur pour un objet transient
	 * @param title titre de la note
	 * @param content contenu de la note
	 */
	public Note(String title, String content){
		this.title = title;
		this.content = content;
	}
	
	/**
	 * constructeur pour un objet transient
	 * @param title titre de la note
	 * @param content contenu de la note
	 */
	public Note(long id, String title, String content){
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	/**
	 * Méthode d'hydratation.<br>
	 * Ne répercute pas l'id (l'objet hydraté étant considéré comme une nouvelle note, il ne peu être déjà persistant)
	 * @param note source pour l'hydratation
	 */
	public void hydrate(Note note){
		this.title = note.title;
		this.content = note.content;
	}

	public long getId() {
		return id;
	}

	/**
	 * à n'utiliser que pour les objets persistants
	 * @param id primary key de l'objet
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", content=" + content
				+ "]";
	}
	
}
