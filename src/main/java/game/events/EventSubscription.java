package game.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventSubscription {

    private static long m_SubscriptionCount = 0;
    private static final List<ISubscription<?>> m_Subscriptions = new ArrayList<>();

    public static abstract class ISubscription<T> {
        protected long m_SubscriptionID;
        protected SubscriptionTopic m_Topic;
        protected Consumer<T> m_Callback;

        public ISubscription(SubscriptionTopic topic, Consumer<T> callback) {
            m_Topic = topic;
            m_Callback = callback;
            m_SubscriptionID = m_SubscriptionCount++;
        }

        public abstract void publish(SubscriptionTopic topic, T value);

        public void unsubscribe() {
            m_Subscriptions.removeIf(sub -> sub.m_SubscriptionID == this.m_SubscriptionID);
        }
    }

    public static class Subscription<T> extends ISubscription<T> {
        private Subscription(SubscriptionTopic topic, Consumer<T> callback) {
            super(topic, callback);
        }

        public static <T> Subscription<T> create(SubscriptionTopic topic, Consumer<T> callback) {
            Subscription<T> sub = new Subscription<>(topic, callback);
            m_Subscriptions.add(sub);
            return sub;
        }

        @Override
        public void publish(SubscriptionTopic topic, T value) {
            if (m_Topic == topic) {
                this.m_Callback.accept(value);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void publishToTopic(SubscriptionTopic topic, T value) {
        m_Subscriptions.stream()
                .filter(sub -> sub.m_Topic == topic)
                .forEach(sub -> {
                    ISubscription<T> typedSubscription = (ISubscription<T>) sub;
                    typedSubscription.publish(topic, value);
                });
    }
}
