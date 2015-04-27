module GuessBid

open util/boolean

//////////////////////////////////////
//////////SIGNATURES////////////
/////////////////////////////////////

sig ExternalUser,Name, Surname, Username, Password, Message {}
//These signatures are considered empty for simplicity, because their attributes are 
//not useful for the alloy modelization.

//String attributes are not considered for simplicity, because 
//they're not useful for the alloy modelization

sig Date{
timestamp: one Int //every date corresponds to a specific timestamp
}{
timestamp>=0
}

sig Auction{
expdate: one Date,
creator: one User,
object: one Object,
bid: set Bid
}

sig User {
username: one Username,
password: one Password,
name: one Name,
surname: one Surname,
balance: one Int,
age: one Int,
createdauction: set Auction,
involvedauction: set Auction,
notification: set Notification,
bid: set Bid
}
{
all disjoint a1,a2: createdauction | a1.object != a2.object 
//the same object can't be auctioned twice by a user

no(createdauction & involvedauction)
//a user can't bid for an auction (be involved in) that has created

}

sig Object{
description: one String,
photo: lone String,
information: one String,
}

sig Notification{
message: lone Message,
receiver: one User
}

sig Bid {
value: Int,
bidder: one User,
relatedauction: one Auction
}


//////////////////////////////////////
////////////FUNCTIONS///////////
/////////////////////////////////////

fun UserAuction [u:User] : set Auction{
		u.involvedauction
}
//given a user, returns all auctions in which user bid

fun UserAuction [u:User] : set Auction{
		u.(involvedauction + createdauction)
}
//given a user, returns all auctions created by user or in which he had participated


//////////////////////////////////////
///////////////FACTS///////////////
/////////////////////////////////////


//Simmetries
fact simmetries{
all a:Auction | a in a.creator.createdauction
all u:User | all a: u.createdauction | u= a.creator
all a:Auction |all u:User | a in u.involvedauction implies (!u in a.creator)
all b:Bid | b in b.relatedauction.bid
all n:Notification | n in n.receiver.notification
all u:User | all n:u.notification | n.receiver = 

u
all u:User | all b:u.bid | b.bidder=u
}

//Duplications
fact noDuplications{
all u1,u2: User | u1.username = u2.username implies u1=u2
all a1,a2: Auction | a1.object = a2.object implies a1=a2
all d1, d2: Date | d1.timestamp = d2.timestamp implies d1=d2
all b1,b2:Bid | (b1.value = b2.value && b1.bidder = b2.bidder) implies b1=b2 
}


//////////////////////////////////////
///////////ASSERTIONS///////////
/////////////////////////////////////

assert UserAuctionSimmetry {
all a: Auction | a in a.creator.createdauction
all u:User | all a:u.createdauction | u=a.creator
}
check UserAuctionSimmetry


assert NoDuplications {
all u1,u2: User | u1.username = u2.username implies u1=u2
all a1,a2: Auction | a1.object = a2.object implies a1=a2
all d1, d2: Date | d1.timestamp = d2.timestamp implies d1=d2
all b1,b2: Bid | (b1.value = b2.value && b1.bidder = b2.bidder) implies b1=b2
}
check NoDuplications

assert Coherence {
#Object = #Auction
}
check Coherence


pred Show() {
#User = 2
}

run Show


//////////////////////////////////////
/////SYSTEM FUNCTIONS////////
/////////////////////////////////////

sig Database {
users : set User,
auctions : set Auction,
bids : set Bid,
notifications: set Notification
}{
#Database = 1
all u:User | u in users
all a:Auction | a in auctions
all b:Bid | b in bids
all n:Notification | n in notifications
}

//database predicates examples
pred addUser[d:Database, u:User] {
u !in d.users implies d.users = d.users+u
}
run addUser 



pred login [eu:ExternalUser, un:Username, p:Password]{
one u:User | u.username = un && u.password = p
}
run login 

