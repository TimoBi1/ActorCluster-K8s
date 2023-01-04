package main;

import java.util.UUID;


import actors.PrimeClusterMasterActor;
import actors.PrimeClusterWorkerActor;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.routing.RandomPool;

public class PrimeClusterApp {

	public static void main(String[] args) {

		// Create an Akka system
		ActorSystem actorSystem = ActorSystem.create("ActorCluster");
		
		//Create Master
		actorSystem.actorOf(ClusterSingletonManager.props(Props.
		create(PrimeClusterMasterActor.class), PoisonPill.getInstance(),
		ClusterSingletonManagerSettings.create(actorSystem)), "master");
		
		//Create RandomPool of 2 Workers
		actorSystem.actorOf(new RandomPool(2).props(Props.create(
		PrimeClusterWorkerActor.class)), "W_" + UUID.randomUUID());
		
	}

}
