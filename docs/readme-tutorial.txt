todo:
-	add db
-	elastic ec2 + LB !! Session stick?
-	get internal ip , and add to all 
-	SIMULATE LOAD and autoscale
=================
-	application overview, port and context, endpoints, resources, actuator
-	unit test
-	Packaging jar and testing locally (no docker for now)
Now
-	regular ec2:
	-	packaging (jar, no docker)
	-	create ec2 
	-	region: oregon
	-	user: root with warning
	-	vpc: default, and check subnet, gateway, Ncl+sec group, LB if several inst
		-	we'll see that in: listening port (incoming), client (out), 'machine' msg
	-	params and env variables
-	WARNING: shut down instances !!! and billing!!

