create table if not exists event(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    date VARCHAR(255)
);

create table if not exists sponsor(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    industry VARCHAR(255)
);

create table if not exists event_sponsor(
    eventid INT,
    sponsorid INT,
    PRIMARY KEY (eventid, sponsorid),
    FOREIGN KEY (eventid) REFERENCES event(id),
    FOREIGN KEY (sponsorid) REFERENCES sponsor(id)
)