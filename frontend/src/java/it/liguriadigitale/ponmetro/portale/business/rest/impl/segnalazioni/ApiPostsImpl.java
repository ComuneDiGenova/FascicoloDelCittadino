package it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazioni;

import it.liguriadigitale.ponmetro.segnalazioni.apiclient.PostsApi;
import it.liguriadigitale.ponmetro.segnalazioni.model.AreaCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.Attachment;
import it.liguriadigitale.ponmetro.segnalazioni.model.AttachmentCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.CategoryCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.Comment;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject10;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject11;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject3;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject4;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject5;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject6;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject7;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject8;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineObject9;
import it.liguriadigitale.ponmetro.segnalazioni.model.InlineResponse2001;
import it.liguriadigitale.ponmetro.segnalazioni.model.NewPost;
import it.liguriadigitale.ponmetro.segnalazioni.model.Post;
import it.liguriadigitale.ponmetro.segnalazioni.model.PostCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.PrivateConversation;
import it.liguriadigitale.ponmetro.segnalazioni.model.PrivateMessage;
import it.liguriadigitale.ponmetro.segnalazioni.model.PublicConversation;
import it.liguriadigitale.ponmetro.segnalazioni.model.ResponseCollection;
import it.liguriadigitale.ponmetro.segnalazioni.model.ResponseSegnalazioni;
import it.liguriadigitale.ponmetro.segnalazioni.model.Timeline;
import org.apache.commons.lang.NotImplementedException;

public class ApiPostsImpl implements PostsApi {

  private PostsApi instance;

  public ApiPostsImpl(PostsApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Attachment addAttachmentsToPostId(Integer var1, InlineObject9 var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Comment addCommentsToPostId(Integer var1, InlineObject3 var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PrivateMessage addPrivateMessageToPostId(Integer var1, InlineObject5 var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public ResponseSegnalazioni addResponsesToPostId(Integer var1, InlineObject7 var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Post createPost(NewPost var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public void deleteAttachmentsInPostId(Integer var1, String var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Comment editCommentsInPostId(Integer var1, Integer var2, InlineObject4 var3) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PrivateMessage editPrivateMessageInPostId(Integer var1, Integer var2, InlineObject6 var3) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public ResponseSegnalazioni editResponsesInPostId(
      Integer var1, Integer var2, InlineObject8 var3) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public AreaCollection getAreasByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public AttachmentCollection getAttachmentsByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public CategoryCollection getCategoriesByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PublicConversation getCommentsByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Post getPostById(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PrivateConversation getPrivateMessagesByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public ResponseCollection getResponsesByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Timeline getTimelineByPostId(Integer var1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PostCollection loadPosts(
      String var1, Integer var2, Integer var3, String var4, String var5) {
    return instance.loadPosts(var1, var2, var3, var4, var5);
  }

  @Override
  public AreaCollection setAreasToPostId(Integer var1, InlineObject10 var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public CategoryCollection setCategoriesToPostId(Integer var1, InlineObject11 var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public InlineResponse2001 setExpiryByPostId(Integer var1, Integer var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Post updatePostById(Integer var1, NewPost var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }
}
