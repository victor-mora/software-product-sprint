package com.google.sps.data;

/** A comment on the page. */
public final class Comment {

  private final long id;
  private final String text;
  private final long timestamp;
  private final String languageCode;

  public Comment(long id, String text, long timestamp, String languageCode) {
    this.id = id;
    this.text = text;
    this.timestamp = timestamp;
    this.languageCode = languageCode;
  }

  public String getText(){
      return this.text;
  }
}