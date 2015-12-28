// In every functions, the variable "entity" defines the entity who uses the script.

/*
	Called on the creation of the entity
*/
function init(entity) {}

/*
	Called when the entity get damages
	
	Parameters:
		from - The entity from who comes the damages
		amount - The amount of damages
*/
function onDamage(entity, from, amount) {}

/*
	Called when the entity get healed
	
	Parameters:
			from - The entity from who comes the heals
			amount - The amount of heals
*/
function onHeal(entity, from, amount) {}

/*
	Called when an entity enter the parent vision
	
	Parameters:
		entity2 - The entity who is in the vision
*/
function onEntityInVision(entity, entity2) {}

/*
	Called on each loop
	
	Parameters:
		delta - The loop delta value (time passed in ms between each loop)
*/
function onUpdate(entity, delta) {}

/*
	Called when an effect is added to the entity
	
	Parameters:
		effect - The effect added
*/
function onEffectAdded(entity, effect) {}

/*
	Called when an effect is remove from the entity
	
	Parameters:
		effect - The effect removed
*/
function onEffectRemoved(entity, effect) {}

/*
	Called when all effects are simultany cleared
*/
function onEffectsCleared(entity) {}

/*
	Called when an entity taunt our entity
	
	Parameters:
		entity2 - The entity who taunted
*/
function onTaunt(entity, entity2) {}

/*
	Called when a collision occures
	
	Parameters:
		entity2 - The collided entity
*/
function onCollision(entity, entity2) {}

