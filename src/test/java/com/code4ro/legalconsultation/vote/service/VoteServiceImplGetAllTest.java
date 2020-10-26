package com.code4ro.legalconsultation.vote.service;

import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.comment.model.persistence.Comment;
import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNode;
import com.code4ro.legalconsultation.security.service.CurrentUserService;
import com.code4ro.legalconsultation.vote.model.dto.VoteDto;
import com.code4ro.legalconsultation.vote.repository.VoteRepository;
import com.code4ro.legalconsultation.vote.model.persistence.Vote;
import com.code4ro.legalconsultation.vote.model.persistence.VoteType;
import com.code4ro.legalconsultation.vote.service.impl.VoteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static com.code4ro.legalconsultation.core.factory.RandomObjectFiller.createAndFill;
import static com.code4ro.legalconsultation.user.model.persistence.UserRole.ADMIN;
import static com.code4ro.legalconsultation.vote.model.persistence.VoteType.DOWN;
import static com.code4ro.legalconsultation.vote.model.persistence.VoteType.UP;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class VoteServiceImplGetAllTest {

    @Mock
    private VoteRepository voteRepository;
    @Mock
    private CurrentUserService currentUserService;
    @InjectMocks
    private VoteServiceImpl voteService;

    private final ApplicationUser currentUser = Objects.requireNonNull(createAndFill(ApplicationUser.class));
    private final UUID uuidComment = randomUUID();

    @Before
    public void before() {
        currentUser.getUser().setRole(ADMIN);
        currentUser.setId(randomUUID());
        when(currentUserService.getCurrentUser()).thenReturn(currentUser);
    }

    @Test
    public void getAnonymousVotes_expectedSingleVoteUp() {
        List<Vote> voteEntities = new ArrayList<>();
        voteEntities.add(createVoteEntity(new ApplicationUser(), UP, uuidComment));
        when(this.voteRepository.findByCommentId(uuidComment)).thenReturn(voteEntities);

        Set<VoteDto> actualVotes = voteService.getAnonymousVotesForComment(uuidComment);

        assertFalse(actualVotes.isEmpty());
        assertEquals(1, actualVotes.size());

        VoteDto voteDto = actualVotes.iterator().next();
        assertEquals(UP, voteDto.getVote());
        assertEquals(uuidComment, voteDto.getCommentId());
    }

    @Test
    public void getAnonymousVotes_expectedSingleVoteDown() {
        List<Vote> voteEntities = new ArrayList<>();
        voteEntities.add(createVoteEntity(new ApplicationUser(), DOWN, uuidComment));
        when(this.voteRepository.findByCommentId(uuidComment)).thenReturn(voteEntities);

        Set<VoteDto> actualVotes = voteService.getAnonymousVotesForComment(uuidComment);

        assertFalse(actualVotes.isEmpty());
        assertEquals(1, actualVotes.size());

        VoteDto voteDto = actualVotes.iterator().next();
        assertEquals(DOWN, voteDto.getVote());
        assertEquals(uuidComment, voteDto.getCommentId());
    }

    @Test
    public void getAnonymousVotes_expectedSingleVoteOfCurrentUserUp() {
        List<Vote> voteEntities = new ArrayList<>();
        voteEntities.add(createVoteEntity(currentUser, UP, uuidComment));
        when(this.voteRepository.findByCommentId(uuidComment)).thenReturn(voteEntities);

        Set<VoteDto> actualVotes = voteService.getAnonymousVotesForComment(uuidComment);

        assertFalse(actualVotes.isEmpty());
        assertEquals(1, actualVotes.size());

        VoteDto voteDto = actualVotes.iterator().next();
        assertEquals(UP, voteDto.getVote());
        assertEquals(uuidComment, voteDto.getCommentId());
    }

    private Vote createVoteEntity(ApplicationUser appUser, VoteType vote, UUID commentId) {
        Vote entity = new Vote();
        entity.setVote(vote);
        Comment comment = new Comment();
        comment.setId(commentId);
        entity.setComment(comment);
        entity.setOwner(appUser);
        return entity;
    }

    private DocumentNode createDocumentNode(UUID uuidDocNode1) {
        DocumentNode documentNode = new DocumentNode();
        documentNode.setId(uuidDocNode1);
        return documentNode;
    }
}
