package cse.bgu.ex5.jokelistbook;


public class Joke {

	private String joke;
	private String author;
	private String date;
	private String likes; // -1 for dislike, 0 for neither, 1 for like 
	private long id;
	


	public Joke() {
		super();
	}

	public Joke (String joke, String author, String date, String likes){
		this.joke = joke;
		this.author = author;
		this.date = date;
		this.likes = likes;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJoke() {
		return joke;
	}
	public void setJoke(String joke) {
		this.joke = joke;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	
    @Override
	public String toString() {
    	String string = this.getAuthor() + "\n" + this.getDate() + "\t\t\t\t\t\t\t\t\t" + this.getLikes();
		return string;
	}


	
}
