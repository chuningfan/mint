package com.mint.service.pipeline;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mint.common.context.UserContext;
import com.mint.service.exception.MintServiceException;
import com.mint.service.pipeline.defaultImpl.AuthValidation;

public class PipelineWorker {
	
	private LinkedList<ServicePipelineMember> members = new LinkedList<>();
	
	public PipelineWorker() {
		members.offerLast(new AuthValidation());
	}
	
	public void appendMember(ServicePipelineMember member, Integer index, boolean replaceExisting) {
		if (member == null) {
			return;
		}
		if (StringUtils.isEmpty(member.id())) {
			return;
		}
		if (isPresent(member.id()) && !replaceExisting) {
			return;
		}
		if (isPresent(member.id()) && replaceExisting) {
			removeMember(member.id());
		}
		if (index == null) {
			members.addLast(member);
		} else {
			members.add(index, member);
		}
	}
	
	public void removeMember(String...ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		Stream.of(ids).forEach(i -> {
			members.removeIf(m -> m.id().equals(i));
		});
	}
	
	private boolean isPresent(String id) {
		Optional<ServicePipelineMember> opt = members.stream().filter(m -> m.id().equals(id)).findAny();
		return opt.isPresent();
	}
	
	public void doProcess(HttpServletRequest req, HttpServletResponse resp, UserContext context) throws MintServiceException {
		if(!members.isEmpty()) {
			for (ServicePipelineMember member: members) {
				member.validate(req, resp, context);
			}
		}
	}
	
	public List<String> addedMembers() {
		if (CollectionUtils.isEmpty(members)) {
			return null;
		}
		return members.stream().map(ServicePipelineMember::id).collect(Collectors.toList());
	}
}
