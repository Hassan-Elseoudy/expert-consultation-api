package com.code4ro.legalconsultation.document.configuration.model.persistence;

import com.code4ro.legalconsultation.core.model.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "document_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentConfiguration extends BaseEntity {

    @Column(name = "is_open_for_commenting")
    private Boolean openForCommenting;

    @Column(name = "is_open_for_voting_comments")
    private Boolean openForVotingComments;
}
