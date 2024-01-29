package com.umc.bobmate.content.service;

import com.umc.bobmate.content.domain.Content;
import com.umc.bobmate.content.domain.ContentType;
import com.umc.bobmate.content.dto.ContentResponse;
import com.umc.bobmate.content.domain.repository.ContentRepository;
import com.umc.bobmate.login.jwt.util.AuthTokensGenerator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public List<ContentResponse> getTop3ContentsByLikes(ContentType contentType) {
        Pageable pageable = PageRequest.of(0, 3);
        List<Content> top3Contents = contentRepository.findTop3ByOrderByLikesCountDesc(contentType, pageable);
        return top3Contents.stream()
                .map(this::mapContentToResponse)
                .collect(Collectors.toList());
    }

    private ContentResponse mapContentToResponse(Content content) {
        return ContentResponse.builder()
                .contentId(content.getId())
                .name(content.getName())
                .imgUrl(content.getImgUrl())
                .linkUrl(content.getLinkUrl())
                .build();
    }



//    public List<ContentResponse> getLikedContentsByMember(Long memberId) {
//        List<Content> likedContents = contentRepository.findByLikesMemberId(memberId);
//        return likedContents.stream()
//                .map(this::mapContentToResponse)
//                .collect(Collectors.toList());
//    }

//    public List<ContentResponse> getTop3Contents(ContentType contentType) {
//        Pageable pageable = PageRequest.of(0, 3);
//        return contentRepository.findTop3ContentsByLikesAndType(contentType, pageable).stream()
//                .map(content -> ContentResponse.builder().contentId(content.getId()).name(content.getName())
//                        .imgUrl(content.getImgUrl()).linkUrl(content.getLinkUrl()).build())
//                .collect(Collectors.toList());
//    }

//    public List<ContentResponse> getAllVideoContents() {
//        List<Content> videoContents = contentRepository.findByType(ContentType.VIDEO);
//        return videoContents.stream()
//                .map((video) -> ContentResponse.builder().contentId(video.getId()).name(video.getName())
//                        .imgUrl(video.getImgUrl()).linkUrl(video.getLinkUrl()).build()).collect(Collectors.toList());
//    }
//
//    public List<ContentResponse> getAllTextContents() {
//        List<Content> textContents = contentRepository.findByType(ContentType.TEXT);
//        return textContents.stream()
//                .map((text) -> ContentResponse.builder().contentId(text.getId()).name(text.getName())
//                        .imgUrl(text.getImgUrl()).linkUrl(text.getLinkUrl()).build()).collect(Collectors.toList());
//    }

}
