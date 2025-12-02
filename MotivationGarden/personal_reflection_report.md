# Personal Reflection on the MotivationGarden Project

## Introduction
I joined the MotivationGarden team to build a game-like planner that joins a simple farm with a to-do list. My work touched both the farm window and the task window, so I saw how the full program fits together. This report looks back on the main outcomes, my personal growth, and the next steps I want to take.

## Outcomes and Highlights
The clearest win was making the shared coin system feel real. When the app starts, the `Main` class gives the player starting coins, sets up the store, and opens both the farm canvas and the task window at the same time. This flow let me see that the launch code must sync data before any screen draws. I also helped fine-tune the starting world. We now plant trees and flowers in random spots and spawn one friendly animal. It sounds simple, but it makes the farm feel alive from the first frame and gives the player a reason to come back.

I am also proud of the to-do list panel. We wanted more than a plain list, so I added buttons that guide the player. The panel now shows the coin total in a bold label, updates the list whenever a task changes, and offers gentle prompts. The add-task dialog gives category ideas like Easy Wins or Movement and fills in task suggestions that players can accept or edit. This design helped me balance friendly language with clear structure so that users can add tasks fast without feeling lost.

## Challenges and Personal Growth
Getting the coin rewards in sync across windows was harder than I expected. The farm store and the to-do list both need to see the same number. To solve this, I leaned on the task manager’s listener list. When a task is done, it adds five coins and then tells every listener to refresh. Wiring that signal into the panel and the store taught me to respect the update order and not block the swing thread. It was a good lesson in keeping UI code light and letting the manager own the state.

Team work was another key learning point. We split the work so one person handled the farm graphics, another focused on task models, and I bridged the pieces. During code reviews we had to agree on shared names and avoid race conditions when the farm loop moved animals. We met twice a week to check that our changes still built, and I learned to write comments that explain why a method exists, not just what it does. This habit made merges smoother and built trust.

## Areas for Improvement
Not everything is done yet. Our task data controller still only prints a stub message and does not save real files. We planned to pull in Gson and write JSON files, but we ran out of time. I take part of the blame for not pushing this earlier. In the future I would schedule the persistence work right after the UI, before polish. The farm also has save and load buttons that do nothing. I want to connect them to the same controller so players do not lose progress.

## Ideas for Extra Content
To keep the project fresh, I listed a few simple assets we could add next:

- **Daily plant cards** with short stories or care tips that unlock after finishing a task. These give players a reward that is quick to read.
- **Seasonal decorations** such as snow piles or lanterns that swap based on the month. They can reuse the same tile grid, so the art load stays light.
- **Sound cues** for key actions, like a soft chime when a task is done or a rustle when planting seeds. Even short clips would make the farm world warmer.
- **Progress postcards** that show the farm layout at the end of each week. Saving a simple screenshot reminds players how much they have grown.

These ideas fit the current code base: the GUI already handles textures, and the task manager can trigger rewards. Adding them would make the garden feel richer without rewriting the core systems.

## Conclusion
This project taught me how a playful interface can motivate daily habits when backed by solid structure. I improved my event-driven thinking, saw the value of quick feedback loops, and learned to speak up sooner when we miss a core feature. Next time, I will plan for data storage earlier and keep user testing in every sprint. Even with the gaps, I feel proud of the garden we grew and ready to take these lessons into the next build.
