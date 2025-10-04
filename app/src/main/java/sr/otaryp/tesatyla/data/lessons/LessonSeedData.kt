package sr.otaryp.tesatyla.data.lessons

internal data class LessonSeed(
    val id: Int,
    val title: String,
    val description: String,
    val teaching: String,
    val steps: List<LessonStepSeed>
)

internal data class LessonStepSeed(
    val number: Int,
    val title: String,
    val theory: String,
    val practice: String
)

internal object LessonSeedData {
    val lessons: List<LessonSeed> = listOf(
        LessonSeed(
            id = 1,
            title = "Lesson 1: Pomodoro Trials",
            description = "Train in timed battles: 25 minutes of focus, 5 minutes of rest.",
            teaching = "This lesson introduces you to the Pomodoro Technique — a method to increase focus and avoid burnout. By working in short, structured sprints, you’ll learn to manage your energy, stay consistent, and reduce mental fatigue.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn the 25–5 Pomodoro structure",
                    theory = "The Pomodoro method divides work into cycles of 25 minutes of focused work followed by 5 minutes of rest. These short sprints allow the brain to recharge regularly, preventing fatigue and increasing concentration. Long, uninterrupted work sessions often reduce effectiveness over time.",
                    practice = "Read about the Pomodoro cycle. Write down the structure somewhere visible: 25 minutes work → 5 minutes rest → repeat 4 times → then a longer break of 15–30 minutes."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Set a 25-minute timer and focus on one task",
                    theory = "Focusing on a single task at a time improves productivity and flow. Multitasking drains attention and slows progress. Commit fully to one task during Pomodoro.",
                    practice = "Pick one task from your to-do list. Close all other tabs, silence notifications, and start a 25-minute timer. Focus solely on this task until the timer ends."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Take a 5-minute break",
                    theory = "Breaks restore attention, improve memory, and allow the brain to process information. Without them, productivity and quality drop.",
                    practice = "Stand up, stretch, or walk during your 5-minute break. Avoid distractions like social media to maintain focus for the next Pomodoro."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Repeat at least 4 times today",
                    theory = "Consistency reinforces the habit and trains your brain to focus in cycles. Repetition builds rhythm and discipline.",
                    practice = "Complete four full Pomodoro cycles today, following the structure you learned."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Write down how your focus changed",
                    theory = "Reflection helps you see progress, identify obstacles, and adjust strategies for improvement.",
                    practice = "At the end of the day, write a short note: How did your focus change? Did tasks feel easier or more manageable?"
                )
            )
        ),
        LessonSeed(
            id = 2,
            title = "Lesson 2: Scrolls of Order",
            description = "Master the art of daily to-do lists to command your kingdom wisely.",
            teaching = "This lesson teaches how to prioritize tasks effectively. By focusing on the most important things first, you’ll reduce overwhelm, increase clarity, and finish meaningful work without feeling scattered.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn the “3 priorities” method",
                    theory = "Limiting yourself to 3 priorities prevents decision fatigue and ensures focus on what really matters. Trying to do too many things at once leads to stress and lower productivity.",
                    practice = "Read about the 3-priority approach and note why limiting tasks works."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Write down 3 tasks: Must Do, Should Do, Nice to Do",
                    theory = "Categorizing tasks by importance helps you clearly see where to start. “Must Do” tasks are critical; “Should Do” tasks are important but less urgent; “Nice to Do” are optional.",
                    practice = "Write today’s tasks in these categories."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Do the “Must Do” task first",
                    theory = "Tackling the most important task first ensures progress on what really matters, even if distractions or fatigue arise later.",
                    practice = "Start with your top “Must Do” task and commit to completing it before anything else."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Review your list at the end of the day",
                    theory = "Reflection helps you understand how realistic your priorities were and adjust planning for tomorrow.",
                    practice = "Check off completed tasks and note unfinished ones for rescheduling."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Reflect on how focusing on fewer tasks felt",
                    theory = "Awareness of your productivity habits improves planning and focus over time.",
                    practice = "Write a short note: Did concentrating on fewer tasks reduce stress? Did it help you finish more important work?"
                )
            )
        ),
        LessonSeed(
            id = 3,
            title = "Lesson 3: Defend Against Distractions",
            description = "Learn strategies to guard your focus from invading chaos.",
            teaching = "This lesson helps you identify and control distractions, improving focus and maintaining deep work sessions.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn how distractions reset focus",
                    theory = "Each interruption requires the brain to refocus, which takes time and energy. Even small distractions reduce efficiency.",
                    practice = "Read about the cognitive cost of interruptions."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Identify your 3 biggest distractions",
                    theory = "Knowing your main distractions allows targeted strategies to minimize them.",
                    practice = "List the top three distractions you face while working (e.g., phone notifications, chat apps, social media)."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Block at least one",
                    theory = "Removing at least one major distraction will make it easier to enter deep focus.",
                    practice = "Turn off notifications, enable “Do Not Disturb,” or close unnecessary apps."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Do a 1-hour deep work session",
                    theory = "Practicing sustained focus strengthens attention muscles and trains resistance to distractions.",
                    practice = "Choose one task and work uninterrupted for 1 hour."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Reflect: what was harder — starting or staying focused?",
                    theory = "Understanding where focus breaks down helps you develop stronger habits.",
                    practice = "Write a note on which part felt more challenging and why."
                )
            )
        ),
        LessonSeed(
            id = 4,
            title = "Lesson 4: The Timekeeper’s Path",
            description = "Harness time blocks to rule your day with clarity and strength.",
            teaching = "Learn to use time blocking to maximize energy and productivity by aligning tasks with your natural rhythms.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn time blocking basics",
                    theory = "Time blocking schedules specific tasks during defined periods, reducing decision fatigue and overcommitment.",
                    practice = "Read about time-blocking methods and benefits."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Look at your energy peaks (morning/afternoon/evening)",
                    theory = "Everyone has periods of high and low energy. Aligning tasks with energy peaks improves output.",
                    practice = "Note when you feel most alert during the day."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Plan tomorrow into blocks (focus, rest, leisure)",
                    theory = "Structuring the day ensures balance between work and recovery, increasing consistency and focus.",
                    practice = "Schedule your tasks into 30–60 minute blocks for tomorrow, including breaks."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Do your hardest task during peak energy",
                    theory = "High-energy periods are ideal for challenging tasks requiring concentration and creativity.",
                    practice = "Complete your most demanding task during your identified energy peak."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Review what worked and adjust for next day",
                    theory = "Reflection allows continuous improvement in planning and productivity.",
                    practice = "Write what worked, what didn’t, and how to adjust tomorrow."
                )
            )
        ),
        LessonSeed(
            id = 5,
            title = "Lesson 5: The Procrastination Dragon",
            description = "Face the beast of delay and strike with proven action techniques.",
            teaching = "This lesson helps you overcome procrastination by breaking down tasks into actionable steps and building momentum.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn why procrastination thrives on vague goals",
                    theory = "Ambiguous or large tasks feel overwhelming, causing avoidance. Clear, concrete actions reduce resistance.",
                    practice = "Read about why specificity combats procrastination."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Pick one task you’ve been avoiding",
                    theory = "Tackling even a small neglected task reduces mental load and builds confidence.",
                    practice = "Select one task you’ve been putting off."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Break it into the smallest possible step",
                    theory = "Micro-steps make starting easy and reduce fear or inertia.",
                    practice = "Write down the first tiny action you can take today."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Do that first step immediately",
                    theory = "Action creates momentum; progress, even small, motivates further action.",
                    practice = "Complete the first micro-step now, without delay."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Note how momentum carried you further",
                    theory = "Reflection reinforces the habit of starting small to defeat procrastination.",
                    practice = "Write how completing the first step affected your motivation to continue."
                )
            )
        ),
        LessonSeed(
            id = 6,
            title = "Lesson 6: The Focus Forge",
            description = "Sharpen your concentration with practical drills. Practice single-tasking, minimize context switching, and build mental endurance for deep work.",
            teaching = "This lesson strengthens the ability to maintain focus and resist multitasking.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn why multitasking weakens focus",
                    theory = "Switching tasks divides attention and reduces efficiency. Single-tasking maximizes cognitive resources.",
                    practice = "Read about the science of multitasking and focus."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Pick one task and commit to finish it",
                    theory = "Commitment enhances focus and ensures completion before moving on.",
                    practice = "Choose one task and block off time to finish it fully."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Write down any distractions instead of acting on them",
                    theory = "Noting distractions prevents derailment while acknowledging them.",
                    practice = "Keep a distraction list nearby. When something pops up, jot it down instead of switching tasks."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Complete the task fully before switching",
                    theory = "Full completion reinforces discipline and avoids unfinished work clutter.",
                    practice = "Finish your chosen task without interruption."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Reflect: how did it feel compared to multitasking?",
                    theory = "Awareness of your workflow improves habits and focus strategy.",
                    practice = "Note differences in energy, speed, and satisfaction."
                )
            )
        ),
        LessonSeed(
            id = 7,
            title = "Lesson 7: Energy Management",
            description = "Learn to align tasks with your natural energy cycles. Discover when you perform best, how breaks recharge you, and why rest is part of productivity.",
            teaching = "Learn to align tasks with natural energy cycles to optimize productivity and avoid burnout.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn how productivity depends on energy cycles",
                    theory = "Mental performance fluctuates during the day. Working against energy peaks reduces efficiency.",
                    practice = "Read about circadian rhythms and productivity patterns."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Track your energy levels (morning, afternoon, evening)",
                    theory = "Observing your own energy helps identify optimal work periods.",
                    practice = "Rate energy levels for different times of day for 1–2 days."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Identify your peak focus period",
                    theory = "Knowing your highest energy window allows strategic scheduling of hard tasks.",
                    practice = "Mark the time of day when concentration is strongest."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Schedule hardest work at that time tomorrow",
                    theory = "Aligning high-effort work with peak energy ensures better output and reduces stress.",
                    practice = "Plan your next day accordingly."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Compare results with today’s performance",
                    theory = "Reflection validates the effectiveness of energy-aligned scheduling.",
                    practice = "Note if productivity improved compared to working at low-energy times."
                )
            )
        ),
        LessonSeed(
            id = 8,
            title = "Lesson 8: Task Alchemy",
            description = "Transform overwhelming projects into clear, actionable steps. Break down big goals into manageable tasks that you can actually finish.",
            teaching = "Learn to break large projects into smaller, manageable tasks, reducing overwhelm and increasing progress visibility.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn why projects overwhelm without breakdown",
                    theory = "Large tasks seem impossible, triggering procrastination. Breaking them into milestones reduces anxiety.",
                    practice = "Read about project decomposition strategies."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Take one big project and split into milestones",
                    theory = "Milestones create mini-goals, making progress tangible.",
                    practice = "Identify 2–3 key milestones for one project."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Break a milestone into tasks",
                    theory = "Concrete tasks transform milestones into actionable steps.",
                    practice = "List specific tasks needed to complete one milestone."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Break one task into sub-tasks",
                    theory = "Micro-actions make starting effortless and maintain momentum.",
                    practice = "Write 2–3 small sub-tasks for one task."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Complete one sub-task today and mark progress",
                    theory = "Visible progress motivates further action and reduces overwhelm.",
                    practice = "Complete a sub-task and note it in your progress tracker."
                )
            )
        ),
        LessonSeed(
            id = 9,
            title = "Lesson 9: The Habit Loop",
            description = "Understand how habits are formed and how to rewire them. Create small daily routines that lead to long-term success.",
            teaching = "Learn to change or build habits using the cue → routine → reward model.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn cue → routine → reward model",
                    theory = "Habits are formed when a cue triggers a routine that provides a reward. Understanding this loop allows behavior modification.",
                    practice = "Read about habit loops and examples."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Identify one unhelpful habit",
                    theory = "Self-awareness is the first step toward change.",
                    practice = "Choose one habit that reduces productivity or focus."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Define a new routine to replace it",
                    theory = "Replacing routines, not eliminating cues, makes habit change sustainable.",
                    practice = "Plan a positive routine that serves the same cue."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Attach the new routine to the same cue",
                    theory = "Consistency strengthens the new habit loop.",
                    practice = "Link your new routine to the old cue."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Test it for 7 days and track with checkmarks",
                    theory = "Repetition solidifies new habits into automatic behavior.",
                    practice = "Follow the new routine daily and mark progress."
                )
            )
        ),
        LessonSeed(
            id = 10,
            title = "Lesson 10: The Reflection Chamber",
            description = "End your day by reviewing wins, lessons, and areas to improve. Build a feedback loop that turns every day into progress.",
            teaching = "Reflection builds self-awareness, helps identify patterns, and improves decision-making and productivity.",
            steps = listOf(
                LessonStepSeed(
                    number = 1,
                    title = "Step 1: Learn why reflection matters for progress",
                    theory = "Regular reflection provides insight into what works, what hinders, and how to plan better.",
                    practice = "Read about the benefits of journaling and reflection."
                ),
                LessonStepSeed(
                    number = 2,
                    title = "Step 2: Each evening, write 3 questions: What did I do? What blocked me? What’s next?",
                    theory = "Structured questions guide productive reflection and learning.",
                    practice = "Prepare a daily reflection sheet with these questions."
                ),
                LessonStepSeed(
                    number = 3,
                    title = "Step 3: Answer them for today",
                    theory = "Documenting actions and obstacles increases accountability and clarity.",
                    practice = "Write your answers honestly each evening."
                ),
                LessonStepSeed(
                    number = 4,
                    title = "Step 4: Repeat for 3 days in a row",
                    theory = "Habitual reflection strengthens learning and self-awareness.",
                    practice = "Continue journaling for 3 consecutive days."
                ),
                LessonStepSeed(
                    number = 5,
                    title = "Step 5: Review your notes and see patterns",
                    theory = "Recognizing trends allows adjustment of strategies for greater efficiency.",
                    practice = "Analyze your notes to find recurring challenges or successes."
                )
            )
        )
    )
}
