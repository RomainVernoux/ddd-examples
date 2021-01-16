# Domain-driven design example applications

A few years ago, when I started learning about Domain-Driven Design through the *blue book*/*red book* and tried to
apply the tactical part of this new vision to my real world projects (mostly Java / Spring / Angular at the time),
I quickly found myself puzzled by issues or limitations coming from the frameworks and tools I used. I also found out
that I was not alone, and that many people in the craftsmanship community didn't really have satisfactory answers to
them.

This repository contains what I wish I had at the time: a few answers to common questions, and complete examples of
working architectures with up-to-date frameworks.

Each directory contains an implementation, with specific languages, frameworks and tools, of the same business use case.
The domain is bike rental, and modeling workshops with domain experts resulted in two aggregates: `bike` and `journey`.
We decided to focus on `bike` first, but we know that commands on bikes have an impact on journeys. For now, we have
identified the following use cases:

- given its location and a radius, a user can see all available bikes nearby
- a bike can be rented by scanning it, which starts a new journey
- the bike position is tracked when the bike moves
- a bike can be returned by scanning it again
- a bike rented by someone else is not available and does not appear in the list of bikes nearby
- a returned bike appears again in the list of bikes nearby

## Disclaimer

Remember that there is no unique answer to all of these issues tackled here, nor any perfect approach or architecture.
Domain-Driven Design is a holistic way to develop software, and therefore can sometimes be quite vague in some aspects.
What is given here is only a simple example of a coherent way to do things in the DDD-way, nothing more :)

## What if I have a question?

Please open a new GitHub discussion in this repository so that everyone can participate!

## Can I contribute?

Yes! if you want to share an example with a particular technical stack, feel free to open a pull-request and I promise
to take a look!
