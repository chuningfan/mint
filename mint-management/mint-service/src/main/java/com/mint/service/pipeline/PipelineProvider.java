package com.mint.service.pipeline;

import java.util.LinkedList;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class PipelineProvider {

	public volatile LinkedList<ServicePipelineMember> prePipeline = Lists.newLinkedList();

	public volatile LinkedList<ServicePipelineMember> postPipeline = Lists.newLinkedList();

	public volatile LinkedList<ServicePipelineMember> afterPipeline = Lists.newLinkedList();

	public void setPre(ServicePipelineMember member, Integer index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(prePipeline);
			if (index == null) {
				newPipeline.addLast(member);
			} else {
				newPipeline.add(index, member);
			}
			prePipeline = newPipeline;
		}
	}
	
	public void removePre(final int index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(prePipeline);
			newPipeline.remove(index);
			prePipeline = newPipeline;
		}
	}
	
	public void removePre(final String id) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(prePipeline);
			newPipeline.removeIf(new Predicate<ServicePipelineMember>() {
				@Override
				public boolean test(ServicePipelineMember t) {
					return t.id().equals(id);
				}
			});
			prePipeline = newPipeline;
		}
	}

	public void setPost(ServicePipelineMember member, Integer index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(postPipeline);
			if (index == null) {
				newPipeline.addLast(member);
			} else {
				newPipeline.add(index, member);
			}
			postPipeline = newPipeline;
		}
	}

	public void removePost(final int index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(postPipeline);
			newPipeline.remove(index);
			postPipeline = newPipeline;
		}
	}
	
	public void removePost(final String id) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(postPipeline);
			newPipeline.removeIf(new Predicate<ServicePipelineMember>() {
				@Override
				public boolean test(ServicePipelineMember t) {
					return t.id().equals(id);
				}
			});
			postPipeline = newPipeline;
		}
	}
	
	public void setAfter(ServicePipelineMember member, Integer index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(afterPipeline);
			if (index == null) {
				newPipeline.addLast(member);
			} else {
				newPipeline.add(index, member);
			}
			afterPipeline = newPipeline;
		}
	}
	
	public void removeAfter(final int index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(afterPipeline);
			newPipeline.remove(index);
			afterPipeline = newPipeline;
		}
	}
	
	public void removeAfter(final String id) {
		synchronized (this) {
			LinkedList<ServicePipelineMember> newPipeline = Lists.newLinkedList(afterPipeline);
			newPipeline.removeIf(new Predicate<ServicePipelineMember>() {
				@Override
				public boolean test(ServicePipelineMember t) {
					return t.id().equals(id);
				}
			});
			afterPipeline = newPipeline;
		}
	}

}
