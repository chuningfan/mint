package com.mint.service.pipeline;

import java.util.LinkedList;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

@Component
public class PipelineProvider {

	public volatile LinkedList<ServicePipelineMember<String>> prePipeline = Lists.newLinkedList();

	public volatile LinkedList<ServicePipelineMember<ModelAndView>> postPipeline = Lists.newLinkedList();

	public volatile LinkedList<ServicePipelineMember<Throwable>> afterPipeline = Lists.newLinkedList();

	public void setPre(ServicePipelineMember<String> member, Integer index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember<String>> newPipeline = Lists.newLinkedList(prePipeline);
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
			LinkedList<ServicePipelineMember<String>> newPipeline = Lists.newLinkedList(prePipeline);
			newPipeline.remove(index);
			prePipeline = newPipeline;
		}
	}
	
	public void removePre(final String id) {
		synchronized (this) {
			LinkedList<ServicePipelineMember<String>> newPipeline = Lists.newLinkedList(prePipeline);
			newPipeline.removeIf(new Predicate<ServicePipelineMember<String>>() {
				@Override
				public boolean test(ServicePipelineMember<String> t) {
					return t.id().equals(id);
				}
			});
			prePipeline = newPipeline;
		}
	}

	public void setPost(ServicePipelineMember<ModelAndView> member, Integer index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember<ModelAndView>> newPipeline = Lists.newLinkedList(postPipeline);
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
			LinkedList<ServicePipelineMember<ModelAndView>> newPipeline = Lists.newLinkedList(postPipeline);
			newPipeline.remove(index);
			postPipeline = newPipeline;
		}
	}
	
	public void removePost(final String id) {
		synchronized (this) {
			LinkedList<ServicePipelineMember<ModelAndView>> newPipeline = Lists.newLinkedList(postPipeline);
			newPipeline.removeIf(new Predicate<ServicePipelineMember<ModelAndView>>() {
				@Override
				public boolean test(ServicePipelineMember<ModelAndView> t) {
					return t.id().equals(id);
				}
			});
			postPipeline = newPipeline;
		}
	}
	
	public void setAfter(ServicePipelineMember<Throwable> member, Integer index) {
		synchronized (this) {
			LinkedList<ServicePipelineMember<Throwable>> newPipeline = Lists.newLinkedList(afterPipeline);
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
			LinkedList<ServicePipelineMember<Throwable>> newPipeline = Lists.newLinkedList(afterPipeline);
			newPipeline.remove(index);
			afterPipeline = newPipeline;
		}
	}
	
	public void removeAfter(final String id) {
		synchronized (this) {
			LinkedList<ServicePipelineMember<Throwable>> newPipeline = Lists.newLinkedList(afterPipeline);
			newPipeline.removeIf(new Predicate<ServicePipelineMember<Throwable>>() {
				@Override
				public boolean test(ServicePipelineMember<Throwable> t) {
					return t.id().equals(id);
				}
			});
			afterPipeline = newPipeline;
		}
	}

}
