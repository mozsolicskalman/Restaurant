using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace BookingSystem.Models
{
    public class Service
    {
        [Key]
        public string Id { get; set; }
        public string Type { get; set; }
        public double DurrationMinutes { get; set; }
    }
}
