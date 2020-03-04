package hexagonal2048.view;

import java.util.*;

abstract class Animation {
	protected List<Runnable> runnables = new ArrayList<>();

	public abstract Animation start();
	public abstract void invokeWhenFinished(Runnable runnable);
}