package hexagonal2048.view;

class ParallelTransition {
	private Animation[] animations;
	private Runnable runnable;

	private int counter = 0;

	public ParallelTransition(Animation... animations) {
		this.animations = animations;
	}

	public ParallelTransition start() {
		if(animations.length == 0)
			this.end();
		else for(Animation anim : animations)
			anim.start().invokeWhenFinished(() -> onAnimationsFinished());
		return this;
	}

	private void onAnimationsFinished() {
		if(this.counter == this.animations.length - 1)
			this.end();
		else {
			this.counter++;
		}
	}

	private void end() {
		this.runnable.run();
	}

	public void invokeWhenFinished(Runnable runnable) {
		this.runnable = runnable;
	}
}