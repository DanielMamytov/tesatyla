package sr.otaryp.tesatyla.data.content

import java.util.Calendar

object InspirationRepository {

    data class Article(
        val id: Int,
        val title: String,
        val content: String
    )

    private val articles = listOf(
        Article(
            id = 1,
            title = "1. Why the Pomodoro Technique Works",
            content = "The Pomodoro Technique is more than just a timer; it’s a structured way to train your brain to focus. Our attention naturally decreases after 20–30 minutes, and pushing beyond that often leads to fatigue and burnout. By splitting work into 25-minute intervals followed by 5-minute breaks, you create a rhythm that respects your brain’s limits. These micro-breaks help prevent decision fatigue, reduce eye strain, and keep your motivation high. Over time, using Pomodoro regularly improves your ability to concentrate for longer sessions. Interestingly, the act of starting a timer creates psychological accountability: it feels like a small commitment, which makes it easier to begin tasks you’ve been avoiding. Instead of thinking, “I must work for hours,” you only commit to 25 minutes. This small shift often eliminates procrastination. For maximum impact, pair the technique with clear task goals. Don’t just “work on the report”; decide which part of the report to finish in one Pomodoro. When you finish four intervals, reward yourself with a longer break. With practice, Pomodoro becomes a discipline tool, teaching your brain to enter deep work faster and stay there longer."
        ),
        Article(
            id = 2,
            title = "2. The Science of To-Do Lists",
            content = "A to-do list may look simple, but it’s one of the most powerful tools for productivity. Psychologists call this effect the “Zeigarnik Effect”: our brain remembers unfinished tasks better than finished ones. That’s why you sometimes can’t relax — your mind is busy keeping track of what still needs to be done. Writing tasks down unloads your brain, reducing stress and freeing mental capacity. However, not all lists are effective. A common mistake is filling them with too many vague tasks, which leads to overwhelm. The key is clarity and priority. Break down large projects into smaller, actionable items. Instead of writing “Work on project,” write “Draft introduction” or “Review data.” Another strategy is to prioritize tasks daily. Identify the top three that will make the biggest impact and start with those. This ensures you’re not just busy, but productive. Research shows that completing tasks also gives a dopamine release, motivating you to keep going. By combining psychological relief with clear priorities, to-do lists transform from a set of reminders into a roadmap that keeps you moving steadily toward your goals."
        ),
        Article(
            id = 3,
            title = "3. How Distractions Hijack Your Brain",
            content = "Every ping from your phone or glance at social media might seem harmless, but it triggers a powerful process in your brain. When distracted, your brain releases dopamine, the same chemical tied to pleasure and reward. This makes distractions addictive, as your brain craves that little “hit” of novelty. The problem is that switching tasks has a heavy cognitive cost. Studies show it takes 15–25 minutes to fully regain focus after a distraction. This constant switching lowers your productivity and increases stress. But there’s good news: awareness is the first defense. By identifying your biggest distractions, you can set boundaries. For digital distractions, turning off notifications or using “Do Not Disturb” mode can help. For physical ones, like noisy environments, noise-cancelling headphones or background music can create a focus bubble. Building habits like checking messages at specific times instead of constantly can also re-train your brain. Protecting your focus is not about strict discipline but about designing an environment where distractions have less power over you. When your attention is guarded, your work quality improves dramatically."
        ),
        Article(
            id = 4,
            title = "4. The Power of Time Blocking",
            content = "Time blocking is a simple yet transformative technique. Instead of keeping a long list of tasks and jumping between them, you assign specific time slots to activities. This creates structure, reduces decision fatigue, and ensures that important work gets done. For example, you might block 9:00–11:00 for deep work, 11:00–12:00 for meetings, and 1:00–2:00 for emails. By doing this, you give every task a “home,” which reduces the stress of wondering when you’ll get to it. Time blocking also helps you see your day more realistically. Many people overestimate how much they can do, leading to frustration. Blocking forces you to confront the limits of your time and make better choices. Another benefit is creating balance. By scheduling rest, learning, and even hobbies, you prevent burnout. Famous thinkers like Benjamin Franklin and modern CEOs use time blocking because it ensures progress on long-term goals, not just daily fires. Over time, this method trains your brain to enter “focus mode” more quickly, since each block has a clear purpose. It’s not just about managing time, but about taking control of your attention and energy."
        ),
        Article(
            id = 5,
            title = "5. Why We Procrastinate — and How to Beat It",
            content = "Procrastination is not simply laziness — it’s often an emotional reaction. When we face a task that feels too big, boring, or uncomfortable, our brain seeks immediate relief by avoiding it. This short-term escape gives temporary comfort but creates long-term stress. Psychologists explain procrastination as a fight between two systems in the brain: the limbic system, which seeks instant pleasure, and the prefrontal cortex, which plans long-term goals. The key to overcoming procrastination is lowering the “entry barrier.” Break tasks into the smallest possible steps, like opening a document or writing one sentence. This reduces the mental resistance and creates momentum. Another powerful method is the “5-minute rule”: promise yourself to work on a task for just 5 minutes. Often, starting dissolves the resistance and you continue naturally. Rewarding progress also helps rewire your brain. Instead of waiting for the whole project to finish, celebrate small wins along the way. Over time, you teach your brain that action, even small, feels better than avoidance. Procrastination may never disappear fully, but with the right tools, you can keep it under control and stay productive."
        ),
        Article(
            id = 6,
            title = "6. The Psychology of Goal Setting",
            content = "Goals are more than wishes; they’re mental anchors that guide attention and action. Psychologists describe goals as “motivational maps” — they give direction, sustain effort, and help us measure progress. However, not all goals are equal. Vague goals like “be more productive” rarely work because they don’t provide clarity. Instead, effective goals are specific and measurable: “write 500 words today” or “finish three tasks before noon.” Another powerful concept is linking goals to personal values. When a goal aligns with what truly matters to you, motivation becomes stronger and longer-lasting. Breaking big goals into smaller milestones also prevents overwhelm. Each small step gives a sense of achievement, releasing dopamine, which reinforces the desire to continue. Writing goals down increases commitment and makes them harder to ignore. Finally, regular reflection is key: review your goals weekly to adjust and realign. Goals are not rigid rules but flexible guides. When used wisely, they turn your daily actions into consistent progress toward meaningful outcomes."
        ),
        Article(
            id = 7,
            title = "7. The Role of Rest in Productivity",
            content = "In a culture that glorifies constant hustle, rest is often seen as wasted time. But neuroscience shows the opposite: rest is when the brain consolidates memories, restores energy, and generates creative insights. Sleep is the most obvious form of rest, but short breaks during the day are equally powerful. Studies show that people who take brief breaks during work maintain higher productivity than those who push through without stopping. Rest is also mental variety: stepping away from a task to take a walk or engage in light activity often sparks new ideas. Athletes understand this principle well — muscles grow stronger not during training but during recovery. The same applies to mental work: sustainable productivity requires cycles of effort and renewal. By planning rest intentionally, like scheduling micro-breaks or ensuring a full lunch break away from screens, you protect your long-term performance. Far from being wasted, rest is an investment. It ensures that when you work, you bring energy, focus, and creativity to the task at hand."
        ),
        Article(
            id = 8,
            title = "8. The Art of Single-Tasking",
            content = "Multitasking is often praised, but research shows it dramatically lowers efficiency. The human brain isn’t wired to focus on two demanding tasks at once. Instead, it rapidly switches between them, losing time and energy with every switch. This constant context shifting reduces accuracy, increases stress, and makes tasks take longer. Single-tasking — focusing on one thing until completion — is a much more powerful approach. It allows your brain to fully engage, enter flow, and produce higher-quality results. Practicing single-tasking requires intentional effort in today’s distraction-filled world. Start by eliminating obvious interruptions: silence notifications, close unused tabs, and keep only the materials you need for the task. Then, set a clear intention: “For the next 30 minutes, I will only work on this report.” Over time, your ability to resist switching strengthens. Single-tasking also brings psychological benefits: finishing a task provides closure, boosting motivation and lowering stress. By choosing depth over speed, you may do fewer tasks in a day, but the impact and quality of your work will be far greater."
        ),
        Article(
            id = 9,
            title = "9. How Environment Shapes Focus",
            content = "Your surroundings influence focus more than you think. A cluttered desk, noisy background, or uncomfortable chair can silently drain energy and reduce concentration. Cognitive science shows that environmental “noise,” whether physical or digital, competes for your brain’s limited attention. By shaping your workspace intentionally, you create conditions where focus comes naturally. Start with physical space: a clean, organized desk reduces mental clutter. Lighting also matters — natural light boosts alertness and mood, while dim spaces encourage fatigue. Sound plays a role too. Some thrive in silence, others with background noise or music without lyrics. The key is awareness: notice what environment helps you enter deep work and recreate it consistently. Digital environment matters as well. Unnecessary notifications, open apps, or constant emails act like invisible noise. Structuring your environment is not about perfection, but about reducing friction. When your space supports your goals, focus feels less like a struggle and more like a natural state."
        ),
        Article(
            id = 10,
            title = "10. The Hidden Cost of Busyness",
            content = "In modern life, being “busy” is often mistaken for being productive. But constant activity doesn’t always equal meaningful progress. Psychologists call this the “action bias” — the tendency to stay in motion just to feel useful. The danger is that busyness can distract from high-value work. Answering emails all day feels active but rarely moves big projects forward. True productivity means making choices about where to direct your limited time and energy. This requires courage: saying no to some tasks, delegating others, and focusing on fewer but more impactful actions. Another hidden cost of busyness is stress. When your schedule is always full, your brain never has room to think strategically or recharge. The result is burnout and declining effectiveness. A healthier approach is to embrace intentional slowness: pause, step back, and ask, “Is what I’m doing moving me toward my goals?” Busyness may impress others, but clarity and focus build lasting results. Productivity is not about doing more — it’s about doing what matters most."
        )
    )

    val dailyTips = listOf(
        "Set your royal quest: choose 3 main goals for today and guard them well.",
        "Train like a knight: 25 minutes of focused work, followed by a short rest to sharpen your sword.",
        "Crown the important tasks first. Royal duties come before urgent whispers.",
        "Banish one distraction from your realm — silence the messenger, close the scroll, guard your focus.",
        "Prepare your royal scroll tonight — tomorrow’s path will be clear and noble.",
        "Gather similar quests together — conquering them as a unit saves your strength.",
        "Pause and reflect: every small victory makes your kingdom stronger."
    )

    fun getArticles(): List<Article> = articles

    fun getArticleById(id: Int): Article? = articles.firstOrNull { it.id == id }

    fun getRandomArticle(): Article = articles.random()

    fun getTipIndexForToday(): Int {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        return (today - 1) % dailyTips.size
    }
}
